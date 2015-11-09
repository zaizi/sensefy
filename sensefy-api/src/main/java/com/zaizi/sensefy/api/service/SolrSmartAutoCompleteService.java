package com.zaizi.sensefy.api.service;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zaizi.sensefy.api.dto.SensefyUser;
import com.zaizi.sensefy.api.dto.response.content.SmartAutoCompleteResponseContent;
import com.zaizi.sensefy.api.dto.response.search.AutoCompleteResponse;
import com.zaizi.sensefy.api.dto.semantic.EntityType;
import com.zaizi.sensefy.api.exception.ComponentCode;
import com.zaizi.sensefy.api.exception.SensefyException;
import com.zaizi.sensefy.api.solr.querybuilder.SecurityQueryBuilder;
import com.zaizi.sensefy.api.utils.SensefyUserMapper;

/**
 * Smart Autocomplete service will provide a smart autocompletion of the user
 * digits recognizing entities, entities properties and simulating a guidance
 * for the user . It's working on 3 phase basis :<br>
 * <li>1) identify the Named Entity instance , the Entity type or a token from
 * suggested fields</li>
 * <li>2) after the first selection, if a type was selected, let's autocomplete
 * on entity types attributes</li>
 * <li>3) after the second selection, we have selected a type and a property, so
 * we autocomplete with values</li>
 *
 * @author Alessandro Benedetti
 * @author Antonio David Perez Morales <aperez@zaizi.com>
 * @since 1.4
 */
@Component
@Service
public class SolrSmartAutoCompleteService extends SolrService {

	private static final Logger logger = LoggerFactory.getLogger(SolrSmartAutoCompleteService.class);

	public static final String ENTITY_TYPE_SUGGESTION_FIELD = "type";
	public static final String ATTRIBUTES_FIELD = "attributes";
	public static final String SEMANTIC_SORTING_FIELD = "occurrences";

	public static final int DEFAULT_NUMBER_SUGGESTIONS = 5;

	public static final int SPELLCHECKING_DISTANCE = 2;
	public static final String DOCUMENT_SUGGESTION = "document_suggestion";
	public static final String SHINGLE_AUTOCOMPLETE_NAME = "ShingleAutocomplete";

	public SolrSmartAutoCompleteService() {
	}

	public SolrSmartAutoCompleteService(String solrBase) {
		super.solrBase = solrBase;
	}

	@PostConstruct
	public void init() throws SensefyException {
		super.init();
	}

	/**
	 * GET method for the first autocomplete phase . <br>
	 * Autocomplete user digit with further information, the user can be typing
	 * a named entity name or a named entity type or a general term
	 * <li>Entity - the named entity names from the label in the entity index
	 * </li>
	 * <li>Entity Type - the entity types from the type in the entity type index
	 * </li>
	 * <li>Title - a title suggestion that contains infix the input token</li>
	 * index
	 *
	 * @param termToComplete
	 *            The term the user is typing and that want to see autocompleted
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query
	 * @return the list of different types of suggestions
	 */
	public AutoCompleteResponse complete1Full(String termToComplete, Integer numberOfSuggestions, boolean security,
			boolean semantic, Principal user) {
		AutoCompleteResponse response = new AutoCompleteResponse();
		SmartAutoCompleteResponseContent responseContent = new SmartAutoCompleteResponseContent();
		response.setResponseContent(responseContent);
		Long startTime = System.currentTimeMillis();
		try {
			numberOfSuggestions = verifyInputParameters(termToComplete, numberOfSuggestions);

			List<String> shingleSuggestions = getShingleSuggestions(numberOfSuggestions, termToComplete,
					this.getPrimaryIndex());
			// suggestions from primary index suggester and entity index
			List<SolrDocument> titleSuggestions = getTitleSuggestions(numberOfSuggestions, termToComplete,
					this.getPrimaryIndex(), user, security);
			
			responseContent.setSuggestions(shingleSuggestions);
			responseContent.setTitles(titleSuggestions);
			responseContent.setNumberOfSuggestions(numberOfSuggestions);
			response.setQuery(termToComplete);

			if (semantic) {
				// First query to suggest named entities, showing mini details
				// <field>:<value>
				QueryResponse namedEntitiesQueryResponse = getSmartSuggestions(termToComplete, numberOfSuggestions,
						this.getEntityCore());

				// Second query to suggest entity types, showing mini details on
				// attributes
				QueryResponse entityTypesQueryResponse = getSmartSuggestions(termToComplete, numberOfSuggestions,
						this.getEntityTypeCore());
				List<SolrDocument> entitiesRetrieved = namedEntitiesQueryResponse.getResults();
				responseContent.setEntities(entitiesRetrieved);
				// This part is related to the multi-language feature. As we
				// have to identify for the entity type which
				// language was matching user query
				List<EntityType> entityTypesRetrieved = entityTypesQueryResponse.getBeans(EntityType.class);
				Map<String, Map<String, List<String>>> highlightingResults = entityTypesQueryResponse.getHighlighting();
				this.processHighlights(entityTypesRetrieved, highlightingResults);
				responseContent.setEntityTypes(entityTypesRetrieved);
			}

		} catch (SensefyException e) {
			processAPIException(response, e, "[Smart Auto Complete] Error - stacktrace follows", 400,
					ComponentCode.QUERY);
		} catch (SolrException e) {
			logger.warn("Wrong input for the autocomplete : " + termToComplete);
		} catch (SolrServerException e) {
			logger.warn("Wrong input for the autocomplete : " + termToComplete);
		} catch (IOException e) {
			logger.warn("Wrong input for the autocomplete : " + termToComplete);
		}

		Long elapsedTime = System.currentTimeMillis() - startTime;
		response.setTime(elapsedTime);

		return response;
	}

	/**
	 * This method return the shingle suggestions for the term the query the
	 * user is typing
	 *
	 * @param numberOfSuggestions
	 * @param termToComplete
	 * @param primaryIndex
	 * @return
	 */
	private List<String> getShingleSuggestions(Integer numberOfSuggestions, String termToComplete,
			SolrServer primaryIndex) throws SolrServerException, SolrException, IOException {
		List<String> shingleSuggestions = new ArrayList<String>();
		SolrQuery shingleSuggestionQuery = new SolrQuery(termToComplete);
		shingleSuggestionQuery.setRequestHandler("/autocomplete");
		shingleSuggestionQuery.set("suggest.count", numberOfSuggestions);
		QueryResponse shingleTitleSuggestionResponse;
		shingleTitleSuggestionResponse = primaryIndex.query(shingleSuggestionQuery);
		shingleSuggestions = parseSuggestionsFromJson(termToComplete, shingleTitleSuggestionResponse);
		return shingleSuggestions;
	}

	private List<String> parseSuggestionsFromJson(String termToComplete, QueryResponse shingleTitleSuggestionResponse) {
		List<String> suggestionTermsList = new ArrayList<String>();
		NamedList autoCompleteNode = (NamedList) ((Map) shingleTitleSuggestionResponse.getResponse().get("suggest"))
				.get(SHINGLE_AUTOCOMPLETE_NAME);
		try{
			SimpleOrderedMap suggestionsNode = (SimpleOrderedMap) autoCompleteNode.get(termToComplete);
			List<SimpleOrderedMap> suggestionList;
			if (suggestionsNode != null) {
				suggestionList = (List<SimpleOrderedMap>) suggestionsNode.get("suggestions");
				for (SimpleOrderedMap suggestion : suggestionList) {
					suggestionTermsList.add((String) suggestion.get("term"));
				}
			}
		}catch(Exception e){
			
		}
		

		return suggestionTermsList;
	}

	/**
	 * this method prevent the document suggestion field to return more than one
	 * value. In this way we would have only one suggestion, and containing the
	 * value can be improved using highlight
	 *
	 * @param titleSuggestions
	 */
	private void filterDocumentSuggestions(List<SolrDocument> titleSuggestions,
			Map<String, Map<String, List<String>>> highlightingSnippets) {
		for (SolrDocument doc : titleSuggestions) {
			String docId = (String) doc.get(ID_FIELD);
			doc.remove(DOCUMENT_SUGGESTION);
			if (highlightingSnippets != null) {
				Map<String, List<String>> field2snippet = highlightingSnippets.get(docId);
				if (field2snippet != null) {
					List<String> snippets = field2snippet.get(DOCUMENT_SUGGESTION);
					if (snippets.size() > 0)
						doc.put(DOCUMENT_SUGGESTION, snippets.get(0));
				}
			}

		}
	}

	/**
	 * Verify the input parameters for the autocomplete is ok .
	 *
	 * @param termToComplete
	 * @param numberOfSuggestions
	 * @return
	 * @throws SensefyException
	 */
	private Integer verifyInputParameters(String termToComplete, Integer numberOfSuggestions) throws SensefyException {
		if (termToComplete == null || termToComplete.isEmpty()) {
			throw new SensefyException(400, "<termToComplete> param required - The term to autocomplete is missing");
		}
		if (numberOfSuggestions == null) {
			numberOfSuggestions = DEFAULT_NUMBER_SUGGESTIONS;
		}
		return numberOfSuggestions;
	}

	/**
	 * Query a specific Solr core to obtain advanced suggestions ( such as
	 * Entity Types or Named entities)
	 *
	 * @param numberOfSuggestions
	 * @param core
	 * @return
	 * @throws SolrServerException
	 */
	private QueryResponse getSmartSuggestions(String termToComplete, Integer numberOfSuggestions, SolrServer core)
			throws SolrServerException, SolrException, IOException {
		SolrQuery namedEntitiesQuery = new SolrQuery();
		this.buildAutocompleteQuery(namedEntitiesQuery, termToComplete);
		namedEntitiesQuery.addSort(SEMANTIC_SORTING_FIELD, SolrQuery.ORDER.desc);
		namedEntitiesQuery.setRows(numberOfSuggestions);
		QueryResponse namedEntitiesQueryResponse;
		namedEntitiesQuery.setRequestHandler("/suggest");
		namedEntitiesQueryResponse = core.query(namedEntitiesQuery);
		if (namedEntitiesQueryResponse.getResults().getNumFound() == 0) {
			buildSpellcheckQuery(namedEntitiesQuery, termToComplete);
			namedEntitiesQueryResponse = core.query(namedEntitiesQuery);
		}
		return namedEntitiesQueryResponse;
	}

	/**
	 * Returns not only autocompletion but also fuzzy similarity
	 *
	 * @param termToComplete
	 * @return
	 */
	private void buildAutocompleteQuery(SolrQuery query, String termToComplete) {
		query.setQuery("\"" + termToComplete + "\"");
	}

	/**
	 * Returns not only autocompletion but also fuzzy similarity
	 *
	 * @param termToComplete
	 * @return
	 */
	private SolrQuery buildSpellcheckQuery(SolrQuery query, String termToComplete) {
		String[] tokens = termToComplete.split("\\s+");
		StringBuilder spellcheckingClause = new StringBuilder();
		for (String s : tokens) {
			int distance = 1 + (s.length() - 1) / 4;
			spellcheckingClause.append(s + "~" + distance + " ");
		}
		String queryString = spellcheckingClause.toString();
		if (tokens.length > 1)
			query.setQuery("\"" + queryString.substring(0, queryString.length() - 1) + "\"");
		else
			query.setQuery(queryString);
		return query;
	}

	/**
	 * Recognize from the entity types suggested, the label the user has typed (
	 * taking care of the language)
	 *
	 * @param entityTypesRetrieved
	 * @param highlightingResults
	 */
	private void processHighlights(List<EntityType> entityTypesRetrieved,
			Map<String, Map<String, List<String>>> highlightingResults) {
		for (EntityType currentEntityType : entityTypesRetrieved) {
			String currentId = currentEntityType.getId();
			Map<String, List<String>> field2snippets = highlightingResults.get(currentId);
			List<String> snippets = field2snippets.get(ENTITY_TYPE_SUGGESTION_FIELD);
			if (snippets.size() != 0) {
				String firstSnippet = snippets.get(0);
				currentEntityType.setLangAwareType(firstSnippet);
			}
		}
	}

	/**
	 * This part is retrieving Infix Title suggestions. It's using a specific
	 * request handler.
	 *
	 * @param numberOfSuggestions
	 * @param termToComplete
	 * @param solrCore
	 * @throws SolrServerException
	 */
	private List<SolrDocument> getTitleSuggestions(int numberOfSuggestions, String termToComplete, SolrServer solrCore,
			Principal user, boolean security) throws SolrServerException, SolrException, IOException {

		SensefyUser sensefyUser = SensefyUserMapper.getSensefyUserFromPrincipal(user);

		SolrDocumentList titleSuggestions = new SolrDocumentList();
		SolrQuery titleSuggestionsQuery = new SolrQuery("\"" + termToComplete + "\"");
		titleSuggestionsQuery.setRequestHandler("/suggest");
		titleSuggestionsQuery.setRows(numberOfSuggestions);
		if (security) {
			String filterQueryACLs = SecurityQueryBuilder.getSecurityFilterQuery(sensefyUser);
			titleSuggestionsQuery.setFilterQueries(filterQueryACLs);
		}
		QueryResponse titleSuggestionResponse;
		titleSuggestionResponse = solrCore.query(titleSuggestionsQuery);
		titleSuggestions = titleSuggestionResponse.getResults();
		if (titleSuggestions.getNumFound() == 0) {
			this.buildSpellcheckQuery(titleSuggestionsQuery, termToComplete);
			titleSuggestionResponse = solrCore.query(titleSuggestionsQuery);
			titleSuggestions = titleSuggestionResponse.getResults();

		}
		this.filterDocumentSuggestions(titleSuggestions, titleSuggestionResponse.getHighlighting());
		return titleSuggestions;
	}

	/**
	 * GET method for the Second phase .<br>
	 * After the first selection of an entity type, this service autocompletes
	 * user digits with a property of that entity type
	 *
	 * @param termToComplete
	 *            The term the user is typing, a property for an entity type is
	 *            expected
	 * @param entityTypeId
	 *            The entity type id already selected from the previous phase
	 * @param numberOfSuggestions
	 *            The number of suggestions requested
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query
	 * @return The autocompleted properties for the selected entity type
	 */
	public AutoCompleteResponse complete2(String termToComplete, String entityTypeId, Integer numberOfSuggestions,
			Principal user) {
		if (numberOfSuggestions == null) {
			numberOfSuggestions = DEFAULT_NUMBER_SUGGESTIONS;
		}
		AutoCompleteResponse response = new AutoCompleteResponse();
		SmartAutoCompleteResponseContent responseContent = new SmartAutoCompleteResponseContent();
		List<String> attributeSuggestions = new ArrayList<String>();
		Long startTime = System.currentTimeMillis();
		try {
			SolrQuery attributesQuery = new SolrQuery(ID_FIELD + ":(" + processHierarchy(entityTypeId) + ")");

			attributeSuggestions = suggestionsFromFacetPrefix(attributesQuery, termToComplete, numberOfSuggestions,
					ATTRIBUTES_FIELD, this.getEntityTypeCore());
			responseContent.setSuggestions(attributeSuggestions);
			responseContent.setNumberOfSuggestions(numberOfSuggestions);
			response.setResponseContent(responseContent);
		} catch (SolrServerException e) {
			processAPIException(response, e, "[Smart Auto Complete] Error - stacktrace follows", 500,
					ComponentCode.SOLR);
		} catch (IOException e) {
			processAPIException(response, e, "[Smart Auto Complete] Error - stacktrace follows", 400,
					ComponentCode.SOLR);
		}
		Long elapsedTime = System.currentTimeMillis() - startTime;
		response.setTime(elapsedTime);
		return response;
	}

	/**
	 * Edismax process :// as special characters that will create a regExp
	 * query, so we need to wrap it in quotes "" each entity type id this fix in
	 * the hierarchy use case This Method is responsible to get all the
	 * hierarchy of ancestors from an EntityTypeId . Example : football team ->
	 * sport team, team ...
	 *
	 * @param entityTypeId
	 * @return
	 */
	private String processHierarchy(String entityTypeId) {
		StringBuilder processedList = new StringBuilder();
		String[] entityTypeIds = entityTypeId.split(",");
		for (int i = 0; i < entityTypeIds.length - 1; i++) {
			processedList.append("\"" + entityTypeIds[i] + "\"" + " OR ");
		}
		processedList.append("\"" + entityTypeIds[entityTypeIds.length - 1] + "\"");
		return processedList.toString();
	}

	/**
	 * GET method for the Third phase .<br>
	 * After the first selection of an entity type, a second selection of a
	 * property,this service autocompletes the user digits with a value for that
	 * property in the specific entity type
	 *
	 * @param termToComplete
	 *            The term the user is typing, a property for an entity type is
	 *            expected
	 * @param entityTypeId
	 *            The entity type id already selected from the previous phase
	 * @param entityAttributeField
	 *            The entity type attribute already selected
	 * @param numberOfSuggestions
	 *            The number of suggestions requested
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query
	 * @return The autocompleted attributes for the selected property of the
	 *         entity type
	 */
	public AutoCompleteResponse complete3(String termToComplete, String entityTypeId, String entityAttributeField,
			Integer numberOfSuggestions, Principal user) {
		if (numberOfSuggestions == null) {
			numberOfSuggestions = DEFAULT_NUMBER_SUGGESTIONS;
		}
		AutoCompleteResponse response = new AutoCompleteResponse();
		SmartAutoCompleteResponseContent responseContent = new SmartAutoCompleteResponseContent();
		List<String> valuesSuggestions = new ArrayList<String>();
		Long startTime = System.currentTimeMillis();
		try {
			SolrQuery valuesQuery = new SolrQuery(ENTITY_TYPE_SUGGESTION_FIELD + ":\"" + entityTypeId + "\"");
			valuesSuggestions = suggestionsFromFacetPrefix(valuesQuery, termToComplete, numberOfSuggestions,
					entityAttributeField, this.getEntityCore());
			responseContent.setSuggestions(valuesSuggestions);
			responseContent.setNumberOfSuggestions(numberOfSuggestions);
			response.setResponseContent(responseContent);
		} catch (SolrServerException e) {
			processAPIException(response, e, "[Smart Auto Complete] Error - stacktrace follows", 500,
					ComponentCode.SOLR);
		} catch (IOException e) {
			processAPIException(response, e, "[Smart Auto Complete] Error - stacktrace follows", 500,
					ComponentCode.SOLR);
		}
		Long elapsedTime = System.currentTimeMillis() - startTime;
		response.setTime(elapsedTime);
		return response;
	}

	/**
	 * <p>
	 * Suggestions from Facet Prefix
	 * </p>
	 *
	 * @param attributesQuery
	 * @param termToComplete
	 * @param numberOfSuggestions
	 * @param suggestionField
	 * @param solrCore
	 * @return
	 * @throws SolrServerException
	 */
	private List<String> suggestionsFromFacetPrefix(SolrQuery attributesQuery, String termToComplete,
			int numberOfSuggestions, String suggestionField, SolrServer solrCore)
					throws SolrServerException, IOException {
		List<String> suggestions = new ArrayList<String>();

		attributesQuery.setRows(0);
		attributesQuery.setFacet(true);
		attributesQuery.addFacetField(suggestionField);
		attributesQuery.setFacetMinCount(1);
		attributesQuery.setFacetLimit(numberOfSuggestions);
		// if ( termToComplete != null && !termToComplete.isEmpty() )
		attributesQuery.setFacetPrefix(suggestionField, termToComplete);

		QueryResponse attributesQueryResponse;
		attributesQueryResponse = solrCore.query(attributesQuery);
		FacetField facetField = attributesQueryResponse.getFacetField(suggestionField);
		List<FacetField.Count> facets = facetField.getValues();
		for (FacetField.Count singleFacet : facets) {
			suggestions.add(singleFacet.getName());
		}
		return suggestions;
	}
}
