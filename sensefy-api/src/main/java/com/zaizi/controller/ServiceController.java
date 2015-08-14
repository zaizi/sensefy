package com.zaizi.controller;

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

import com.zaizi.dto.response.search.SearchResponse;
import com.zaizi.service.SearchService;

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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "Hello World";

	}

	@RequestMapping(value = "/keywordSearch", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody SearchResponse keywordBasedSearch(@RequestParam String query, @RequestParam String fields,
			@RequestParam String filters, @RequestParam boolean facet,
			@RequestParam(required = true) int start, @RequestParam Integer rows, @RequestParam String order,
			@RequestParam boolean spellcheck, @RequestParam boolean clustering, @RequestParam String clusteringSort,
			Principal user) {
		logger.info("Keyword search");

		return searchService.getSearchResponse(query, fields, filters, start, rows, order, facet, spellcheck,
				clustering, clusteringSort, true);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}
