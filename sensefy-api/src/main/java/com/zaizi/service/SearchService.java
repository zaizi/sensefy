package com.zaizi.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaizi.dto.faceting.configuration.FacetConfigurationList;
import com.zaizi.dto.faceting.configuration.FacetConfigurer;
import com.zaizi.dto.response.content.SearchResults;
import com.zaizi.dto.response.search.SearchResponse;
import com.zaizi.exception.ComponentCode;
import com.zaizi.exception.SensefyException;
import com.zaizi.solr.clustering.ClusterParser;
import com.zaizi.solr.faceting.FacetParser;
import com.zaizi.solr.querybuilder.QueryBuilder;

@Service
public class SearchService extends SolrService {

	private static Log logger = LogFactory.getLog(SearchService.class);
	
	@Autowired
	private FacetConfigurer facetConfigurers;

	/*
	 * this field stores in each entity all the document ids containing the
	 * entity
	 */
	protected static final String DOC_ID_FIELD = "doc_ids";

	public SearchService() {
	}

	public SearchService(String solrBase) {
		super.solrBase = solrBase;
	}

	@PostConstruct
	public void init() throws SensefyException {
		super.init();
	}
	
	
	public SearchResponse getSearchResponse(String query, String fields, String filters, int start, Integer rows, String order, boolean facet, boolean spellcheck, boolean clustering,String clusterSort,boolean security) {
		SearchResponse response = new SearchResponse();
		
		
		response.setQuery(query);
        SearchResults responseContent = new SearchResults();
        responseContent.setStart(start);
        Long startTime = System.currentTimeMillis();
        try
        {
            FacetConfigurationList facetConfiguration=null ;
            if(facet)
                facetConfiguration= facetConfigurers.getFacetConfiguration();
            SolrQuery documentsQuery = QueryBuilder.getSolrQuery(query, fields, facet,
                    facetConfiguration, filters, start, rows, order, security, spellcheck,
                    clustering);

            QueryResponse primaryIndexResponse;
            primaryIndexResponse = this.getPrimaryIndex().query(documentsQuery);
            Map<String, Map<String, List<String>>> highlightingSnippets = primaryIndexResponse.getHighlighting();
            SpellCheckResponse spellCheckResponse = primaryIndexResponse.getSpellCheckResponse();
            String collationQuery;
            if (spellCheckResponse != null)
            {
                collationQuery = spellCheckResponse.getCollatedResult();
                responseContent.setCollationQuery(collationQuery);
            }
            SolrDocumentList primaryIndexResults = primaryIndexResponse.getResults();
            responseContent.setNumFound(primaryIndexResults.getNumFound());
            responseContent.setDocuments(primaryIndexResults);
            responseContent.setHighlight(highlightingSnippets);
            response.setSearchResults(responseContent);
            if(clustering){
                if(clusterSort!=null && clusterSort.equals("size"))
                    ClusterParser.parseClusters(primaryIndexResponse, response,true);
                else
                    ClusterParser.parseClusters(primaryIndexResponse, response,false);
            }
            if(facet)
                FacetParser.parseFacets(primaryIndexResponse, response, facetConfiguration);
        }
        catch (SolrServerException e)
        {
            processAPIException(response, e, "[Keyword Based Search] Error - stacktrace follows", 500,
                    ComponentCode.SOLR);
        }
        Long elapsedTime = System.currentTimeMillis() - startTime;
        response.setTime(elapsedTime);
		
		return response;
	}
	
}
