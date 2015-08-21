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

	@RequestMapping(value = "/keywordSearch", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody SearchResponse keywordBasedSearch(@RequestParam String query, @RequestParam String fields,
			@RequestParam String filters, @RequestParam boolean facet, @RequestParam(required = true) int start,
			@RequestParam Integer rows, @RequestParam String order, @RequestParam boolean spellcheck,
			@RequestParam boolean clustering, @RequestParam String clusteringSort, Principal user) {

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

	@RequestMapping(value = "/autocomplete/2", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public AutoCompleteResponse complete2(@RequestParam String termToComplete, @RequestParam String entityTypeId,
			@RequestParam Integer numberOfSuggestions, Principal user) {
		logger.info("Autocomplete search Phase 2");
		return smartSearch.complete2(termToComplete, entityTypeId, numberOfSuggestions, user);
	}

	@RequestMapping(value = "/autocomplete/3", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public AutoCompleteResponse complete3(@RequestParam String termToComplete, @RequestParam String entityTypeId,
			@RequestParam String entityAttributeField, @RequestParam Integer numberOfSuggestions, Principal user) {
		logger.info("Autocomplete search Phase 3");
		return smartSearch.complete3(termToComplete, entityTypeId, entityAttributeField, numberOfSuggestions, user);
	}
}
