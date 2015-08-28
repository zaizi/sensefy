package com.zaizi.sensefy.api.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zaizi.sensefy.api.dto.response.search.AutoCompleteResponse;
import com.zaizi.sensefy.api.dto.response.search.SearchResponse;
import com.zaizi.sensefy.api.service.SearchService;
import com.zaizi.sensefy.api.service.SolrSmartAutoCompleteService;

/**
 * Rest Service controller endpoint
 * 
 * @author mfahiz
 * @since 2.0
 */

@RestController
public class ServiceController extends WebMvcAutoConfigurationAdapter {

	private static Log logger = LogFactory.getLog(ServiceController.class);

	@Autowired
	private SearchService searchService;

	@Autowired
	private SolrSmartAutoCompleteService smartSearch;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "Hello World";

	}

	/**
	 * Provides a classic keyword search approach on our primaryIndex. This
	 * index contains the documents extracted from the original data sources.
	 * This service will return a set of documents relevant to the query
	 * submitted.
	 * 
	 * @param query
	 *            The keyword based query
	 * @param fields
	 *            The list of fields to return in the output documents
	 * @param filters
	 *            A filter query to obtain a subset of the documents relevant to
	 *            the main query
	 * @param facet
	 *            If enabled the relevant results will contain the facet
	 *            countings
	 * @param start
	 *            The first document to return in the list of relevant documents
	 * @param rows
	 *            The number of documents to return
	 * @param order
	 *            The sorting order for the results : <field> <direction> eg:
	 *            title_sort desc
	 * @param spellcheck
	 *            If enabled the result will return a suggestion for a mispelled
	 *            query
	 * @param clustering
	 * @param sensefyToken
	 *            The Sensefy Token that contains relevant information for the
	 *            user running the query @return A json representation of the
	 *            list of relevant documents for the input query
	 */
	@RequestMapping(value = "/keywordSearch", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody SearchResponse keywordBasedSearch(@RequestParam String query, @RequestParam String fields,
			@RequestParam String filters, @RequestParam boolean facet, @RequestParam int start,
			@RequestParam Integer rows, @RequestParam String order, @RequestParam boolean spellcheck,
			@RequestParam boolean clustering, @RequestParam(required = false) String clusteringSort, Principal user) {

		logger.info("Keyword search");
		return searchService.getSearchResponse(query, fields, filters, start, rows, order, facet, spellcheck,
				clustering, clusteringSort, true, user);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
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
	@RequestMapping(value = "/autocomplete/1", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public AutoCompleteResponse complete1(@RequestParam String termToComplete,
			@RequestParam Integer numberOfSuggestions, @RequestParam boolean semantic, Principal user) {
		logger.info("Autocomplete search Phase 1");
		return smartSearch.complete1Full(termToComplete, numberOfSuggestions, true, semantic, user);
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
	@RequestMapping(value = "/autocomplete/2", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public AutoCompleteResponse complete2(@RequestParam String termToComplete, @RequestParam String entityTypeId,
			@RequestParam Integer numberOfSuggestions, Principal user) {
		logger.info("Autocomplete search Phase 2");
		return smartSearch.complete2(termToComplete, entityTypeId, numberOfSuggestions, user);
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
	@RequestMapping(value = "/autocomplete/3", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public AutoCompleteResponse complete3(@RequestParam String termToComplete, @RequestParam String entityTypeId,
			@RequestParam String entityAttributeField, @RequestParam Integer numberOfSuggestions, Principal user) {
		logger.info("Autocomplete search Phase 3");
		return smartSearch.complete3(termToComplete, entityTypeId, entityAttributeField, numberOfSuggestions, user);
	}
}
