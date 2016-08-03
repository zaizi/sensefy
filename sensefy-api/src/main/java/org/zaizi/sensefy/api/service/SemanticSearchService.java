/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
package org.zaizi.sensefy.api.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfigurer;
import org.zaizi.sensefy.api.dto.response.content.SearchResults;
import org.zaizi.sensefy.api.dto.response.search.SearchResponse;
import org.zaizi.sensefy.api.dto.semantic.EntityType;
import org.zaizi.sensefy.api.exception.ComponentCode;
import org.zaizi.sensefy.api.exception.SensefyException;
import org.zaizi.sensefy.api.solr.faceting.FacetParser;
import org.zaizi.sensefy.api.solr.querybuilder.QueryBuilder;
import org.zaizi.sensefy.api.utils.PrimaryDocumentUtils;

/**
 * Provides the functionalities to trigger Semantic Searches from the smart
 * AutoComplete feature.<br>
 * This implementation is currently strictly related to Sensefy index structure
 * and based on inter-index query time join
 * <p/>
 * The main target of this service is to provide a search based on entities
 * instead of keywords.
 *
 * @Author: Alessandro Benedetti
 * @author Antonio David Perez Morales <aperez@zaizi.com> Date: 12/02/2014
 *
 */
@Component
@Service
public class SemanticSearchService extends SolrService {
	private static final Logger logger = LoggerFactory.getLogger(SemanticSearchService.class);

	@Autowired
	private FacetConfigurer facetConfigurer;

	public FacetConfigurer getFacetConfigurer() {
		return facetConfigurer;
	}

	public void setFacetConfigurer(FacetConfigurer facetConfigurer) {
		this.facetConfigurer = facetConfigurer;
	}
	// this field stores in each entity all the document ids containing the entity
	protected static final String DOC_ID_FIELD = "doc_ids";
	protected static final String ENTITY_DRIVEN_HIGHLIGH_FIELD = "label";
	   protected static final String ENTITY_DRIVEN_HIGHLIGH_QUERY_PARAM = "hl.q";


	public SemanticSearchService() {
	}

	public SemanticSearchService(String solrBase) {
		super.solrBase = solrBase;
	}

	@PostConstruct
	public void init() throws SensefyException {
		super.init();
	}

	/**
	 * Returns a set of relevant documents to an entity or an entity group in
	 * input<br>
	 * Is possible to search for a specific entity instance, for an entity type
	 * and possibly an entity type with some specific attribute<br>
	 * This service is called with the information retrieved from the Smart
	 * Autocomplete phases.
	 * 
	 * @param entityIds
	 *            The unique id for the entity of interest
	 * @param entityType
	 *            The unique id for the entity type of interest
	 * @param entityAttribute
	 *            An attribute for the entity type in input eg. nationality for
	 *            the entity type: person
	 * @param entityAttributeValue
	 *            A value of interest for the entity attribute in input eg.
	 *            italian for the attribute nationality
	 * @param fields
	 *            The list of fields to return in the output documents
	 * @param filters
	 *            A filter query to obtain a subset of the documents relevant to
	 *            the main query
	 * @param start
	 *            The first document to return in the list of relevant documents
	 * @param rows
	 *            The number of documents to return
	 * @param order
	 *            The sorting order for the results : <field> <direction> eg:
	 *            title_sort desc
	 * @param facet
	 *            If enabled the relevant results will contain the facet
	 *            countings
	 * @param security
	 *            If enabled the relevant results will be filtered based on user
	 *            permissions
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query
	 * @return A json representation of the list of relevant documents for the
	 *         input query
	 */
	public SearchResponse entityDrivenSearch(List<String> entityIds, String entityType, String entityAttribute,
			String entityAttributeValue, String fields, String filters, int start, Integer rows, String order,
			boolean facet, boolean security, Principal user, boolean clustering) {
		String stringQuery;
		QueryResponse queryResponse;
		SearchResponse response = new SearchResponse();
		SearchResults responseContent = new SearchResults();
		Long startTime = System.currentTimeMillis();
		try {

			if ((entityType == null || entityType.isEmpty()) && (entityIds == null || (entityIds.size() <= 0))) {
				throw new SensefyException(400, "<entityType> or <entityId> param required");
			}

			stringQuery = QueryBuilder.getQueryString(entityIds, entityType, entityAttribute, entityAttributeValue,
					response);

			SolrQuery solrQuery = QueryBuilder.getSolrQuery(stringQuery, fields, facet,
					facetConfigurer.getFacetConfiguration(), filters, start, rows, order, security, false, clustering,
					user);
			// retrieve the entity
			List<SolrDocument> extractedEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for (int i=0; i<entityIds.size(); i++) {
				SolrDocument extractedEntity = getEntity(entityIds.get(i));
				extractedEntities.add(extractedEntity);
				String labelVal = (String) extractedEntity.getFieldValue(ENTITY_DRIVEN_HIGHLIGH_FIELD);
				sb.append("\"");
				sb.append(labelVal);
				sb.append("\"");
				if(i != entityIds.size() -1){
					sb.append("AND");
				}
			}
			sb.append(")");
			responseContent.setEntity(extractedEntities);
//			// retrieve the entity type
//			EntityType extractedEntityType = getEntityType(entityType);
//			responseContent.setEntityType(extractedEntityType);

			
			logger.debug("Entity label for the entity driven search : " + sb.toString());
			solrQuery.set(ENTITY_DRIVEN_HIGHLIGH_QUERY_PARAM, sb.toString());
			queryResponse = this.getPrimaryIndex().query(solrQuery);			
		    //get the highlights
			 Map<String, Map<String, List<String>>> highlightingSnippets = queryResponse.getHighlighting();
			
			SolrDocumentList docsRetrievedFromEntity = queryResponse.getResults();
			FacetParser.parseFacets(queryResponse, response, facetConfigurer.getFacetConfiguration());
			responseContent.setStart(start);
			responseContent.setNumFound(docsRetrievedFromEntity.getNumFound());
			responseContent.setDocuments(docsRetrievedFromEntity);
			responseContent.setHighlight(highlightingSnippets);
			
			response.setSearchResults(responseContent);
		} catch (SensefyException e) {
			processAPIException(response, e, "[Entity Driven Search] Error - stacktrace follows", 400,
					ComponentCode.QUERY);
		} catch (SolrServerException e) {
			processAPIException(response, e, "[Entity Driven Search] Error - stacktrace follows", 500,
					ComponentCode.SOLR);
		} catch (IOException e) {
			processAPIException(response, e, "[Entity Driven Search] Error - stacktrace follows", 400,
					ComponentCode.QUERY);
		}

		Long elapsedTime = System.currentTimeMillis() - startTime;
		response.setTime(elapsedTime);
		return response;
	}

	/**
	 * Returns all the entities occurring in a document in input.<br>
	 * If a selection over the entities was placed with a filter, the entities
	 * returned will be filtered accordingly to that.
	 *
	 * @param docId
	 *            The unique id for the document in input
	 * @param fields
	 *            The list of fields to return in the output documents
	 * @param filters
	 *            A filter query to obtain a subset of the documents relevant to
	 *            the main query
	 * @param start
	 *            The first document to return in the list of relevant documents
	 * @param rows
	 *            The number of documents to return
	 * @param sort
	 *            The sorting order for the results : <field> <direction> eg:
	 *            title_sort desc
	 * @param security
	 *            If enabled the relevant results will be filtered based on user
	 *            permissions
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query
	 * @return A json representation of the list of relevant entities for the
	 *         input document id
	 */
	public SearchResponse showEntitiesByDocId(String docId, String fields, String filters, int start, Integer rows,
			String sort, boolean security, Principal user) {
		SearchResponse response = new SearchResponse();
		SearchResults responseContent = new SearchResults();
		Long startTime = System.currentTimeMillis();
		try {
			docId = escapeDocId(docId);

			if (docId == null || docId.isEmpty()) {
				throw new SensefyException(400, "<query> param required - The query is missing");
			}

			SolrQuery solrQuery = QueryBuilder.getSolrQuery(DOC_ID_FIELD + ":\"" + docId + "\"", fields, false, null,
					null, start, rows, sort, security, false,false, user);
			if (filters != null && !filters.equals("")) {
				solrQuery.setFilterQueries(filters);
			}

			QueryResponse queryResponse;
			queryResponse = this.getEntityCore().query(solrQuery);

			SolrDocumentList entitiesRetrieved = queryResponse.getResults();
			responseContent.setDocuments(entitiesRetrieved);
			responseContent.setNumFound(entitiesRetrieved.getNumFound());
			response.setSearchResults(responseContent);
		} catch (SensefyException e) {
			processAPIException(response, e, "[Show Entity By Doc Id] Error - stacktrace follows", 400,
					ComponentCode.QUERY);
		} catch (MalformedURLException e) {
			processAPIException(response, e, "[Show Entity By Doc Id] Error - stacktrace follows", 500,
					ComponentCode.QUERY);
		} catch (SolrServerException e) {
			processAPIException(response, e, "[Show Entity By Doc Id] Error - stacktrace follows", 500,
					ComponentCode.SOLR);
		} catch (IOException e) {
			processAPIException(response, e, "[Show Entity By Doc Id] Error - stacktrace follows", 400,
					ComponentCode.QUERY);
		}

		Long elapsedTime = System.currentTimeMillis() - startTime;
		response.setTime(elapsedTime);
		return response;
	}

	/**
	 * Get an Entity from the EntityCore using the Solr GET on that core
	 *
	 * @param entityId
	 * @return
	 * @throws org.apache.solr.client.solrj.SolrServerException
	 * @throws IOException 
	 */
	private SolrDocument getEntity(String entityId) throws SolrServerException, IOException {
		SolrDocument extractedEntity = null;
		if (entityId != null && !entityId.isEmpty()) {
			SolrQuery entityQuery = new SolrQuery();
			entityQuery.setRequestHandler("/get");
			entityQuery.set("ids", entityId);
			entityQuery.setFields("description","thumbnail","label", "id", "thumbnail_base64");
			QueryResponse entityResponse;
			entityResponse = this.getEntityCore().query(entityQuery);
			List<SolrDocument> entities = entityResponse.getResults();
			if (entities.size() > 0) {
				extractedEntity = entities.get(0);
			}
		}
		return extractedEntity;
	}

	/**
	 * return an Entity type after a query from the entity type core
	 *
	 * @param entityType
	 * @return
	 * @throws org.apache.solr.client.solrj.SolrServerException
	 * @throws IOException 
	 */
	private EntityType getEntityType(String entityType) throws SolrServerException, IOException {
		EntityType extractedEntityType = null;
		SolrQuery entityTypeQuery = new SolrQuery(ID_FIELD + ":\"" + entityType + "\"");
		QueryResponse entityResponse;
		entityResponse = this.getEntityTypeCore().query(entityTypeQuery);
		List<EntityType> entityTypes = entityResponse.getBeans(EntityType.class);

		if (entityTypes.size() > 0) {
			extractedEntityType = entityTypes.get(0);
		}

		return extractedEntityType;
	}

	/**
	 * Escape the documend ID to prevent problems with slashes and special
	 * characters in the solr query.
	 *
	 * @param docId
	 * @return
	 * @throws java.net.MalformedURLException
	 */
	private String escapeDocId(String docId) throws MalformedURLException {
		String namespace = PrimaryDocumentUtils.getNamespace(docId);
		String localName = PrimaryDocumentUtils.getLocalName(docId);
		if (!localName.equals(docId)) {
			docId = namespace + PrimaryDocumentUtils.escapeLocalPrimaryDocumentId(localName);
		}
		return docId;
	}
}
