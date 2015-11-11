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
package org.zaizi.sensefy.api.solr.querybuilder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.zaizi.sensefy.api.controller.ServiceController;
import org.zaizi.sensefy.api.dto.SensefyUser;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfiguration;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfigurationList;
import org.zaizi.sensefy.api.dto.response.search.SearchResponse;
import org.zaizi.sensefy.api.exception.SensefyException;
import org.zaizi.sensefy.api.utils.SensefyUserMapper;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;

/**
 * This class is responsible of building the solr query
 * 
 * @Author: Alessandro Benedetti Date: 16/05/2014
 */
public class QueryBuilder {

	private static Log logger = LogFactory.getLog(ServiceController.class);
	/* Primary Index */
	public static final String SMLT_ENTITIES = "smlt_entities";

	public static final String SMLT_ENTITY_TYPES = "smlt_entity_types";

	/* Entity index */
	public static final String ID_FIELD = "id";

	public static final String DOC_ID_FIELD = "doc_ids";

	public static final int DEFAULT_ROWS = 10;

	/* Entity Type Index */

	public static final String ENTITY_TYPE_ID_FIELD = "type";

	/* Index Names */

	public static final String SOLR_ENTITY_CORE = "entity";

	/**
	 * return a solr query built with all the parameters in input . Spellcheck
	 * included
	 * 
	 * @param query
	 * @param fields
	 * @param filters
	 * @param start
	 * @param rows
	 * @param security
	 * @param clustering
	 * @param sensefyToken
	 * @return
	 * @throws SensefyException
	 */
	public static SolrQuery getSolrQuery(String query, String fields, boolean facet, FacetConfigurationList facetConfig,
			String filters, int start, Integer rows, String order, boolean security, boolean spellcheck,
			boolean clustering, Principal user) {

		SensefyUser sensefyUser = SensefyUserMapper.getSensefyUserFromPrincipal(user);

		if (rows == null) {
			rows = DEFAULT_ROWS;
		}
		// retrieve the documents from primary index
		SolrQuery documentsQuery = new SolrQuery(query);
		Set<String> selectedFields = new HashSet<String>();
		documentsQuery.setRows(rows);
		documentsQuery.set("spellcheck", spellcheck);
		documentsQuery.set("clustering", clustering);
		if (fields != null && !fields.isEmpty()) {
			String[] fieldsArray = fields.split(",");
			documentsQuery.setFields(fieldsArray);
		}
		if (filters != null && !filters.isEmpty()) {
			String[] filtersArray = buildFilterQueries(filters.split(","), selectedFields);
			documentsQuery.setFilterQueries(filtersArray);
		}
		if (order != null && !order.isEmpty()) {
			documentsQuery.set(CommonParams.SORT, order);
		}

		if (security) {
			
			String filterQueryACLs = SecurityQueryBuilder.getSecurityFilterQuery(sensefyUser);
			documentsQuery.addFilterQuery(filterQueryACLs);

		}
		documentsQuery.set("TZ", sensefyUser.getTimezone().getID());
		documentsQuery.setStart(start);
		ModifiableSolrParams querySolrParams = new ModifiableSolrParams();
		if (facet) {
			// facets must be created with proper exclusion tag where needed
			documentsQuery.setFacet(true);
			List<FacetConfiguration> facetConfigurations = facetConfig.getFacetConfigurations();
			for (FacetConfiguration facetConfiguration : facetConfigurations) {
				String tag = null;
				if (selectedFields.contains(facetConfiguration.getField()))
					tag = "tag-" + facetConfiguration.getField();
				facetConfiguration.getSolrFacetParams(querySolrParams, tag);
			}
		}
		documentsQuery.add(querySolrParams);
		return documentsQuery;
	}

	/**
	 * takes in input a set of filter queries generate from the UI . Those
	 * filter queries will be built with the appropriate tag : Input:
	 * ds_creator_sort:"fran", ds_creator_sort:"tico", author:"jan" Expected
	 * result: 1 for each field fq={!tag=tag-ds_creator}ds_creator_sort:("fran"
	 * OR "tico") fq={!tag=tag-author}author:("fran" OR "tico")
	 *
	 * @return
	 */
	private static String[] buildFilterQueries(String[] filterQueries, Set<String> selectedFields) {
		ArrayListMultimap<String, String> field2filterQueries = ArrayListMultimap.create();
		ArrayList<String> finalFilterQueries = new ArrayList<String>();
		for (String fq : filterQueries) {
			String[] split = fq.split(":");
			field2filterQueries.put(split[0], split[1]);
			selectedFields.add(split[0]);
		}
		// {!tag=tag-ds_creator}ds_creator_sort:("fran" OR "tico")
		for (String field : field2filterQueries.keySet()) {
			List<String> queries = field2filterQueries.get(field);
			String disJunctionClause = buildDisClause(queries);
			String filterQuery = "{!tag=tag-" + field + "}" + field + ":(" + disJunctionClause + ")";
			finalFilterQueries.add(filterQuery);
		}
		return finalFilterQueries.toArray(new String[finalFilterQueries.size()]);
	}

	/**
	 * Takes all the different queries for a field, building a disjunction
	 * 
	 * @param queries
	 * @return
	 */
	private static String buildDisClause(List<String> queries) {
		String disClause = Joiner.on(" OR ").join(queries);
		return disClause;
	}

	/**
	 * Return the proper semantic query string, based on the params in input. In
	 * this way we can build semantic search by entityId,entity type or entity
	 * attributes and return the query string
	 *
	 * @param entityId
	 * @param entityType
	 * @param entityAttribute
	 * @param entityAttributeValue
	 * @return
	 */
	public static String getQueryString(String entityId, String entityType, String entityAttribute,
			String entityAttributeValue, SearchResponse searchResponse) {
		String query;
		String baseQuery = "";
		if (entityId != null) {
			query = SMLT_ENTITIES + ":\"" + entityId + "\"";
			baseQuery = query;
		} else {
			if (entityAttribute == null) {
				query = SMLT_ENTITY_TYPES + ":\"" + entityType + "\"";
				baseQuery = query;
			} else {
				baseQuery = ENTITY_TYPE_ID_FIELD + ":\"" + entityType + "\" AND " + entityAttribute + ":\""
						+ entityAttributeValue + "\"";
				query = getJoinQuery(DOC_ID_FIELD, ID_FIELD, SOLR_ENTITY_CORE, baseQuery);
			}
		}
		searchResponse.setQuery("(" + baseQuery + ")");
		return query;
	}

	/**
	 * Return the join Solr query
	 *
	 * @param from
	 * @param to
	 * @param fromIndex
	 * @param query
	 * @return
	 */
	private static String getJoinQuery(String from, String to, String fromIndex, String query) {
		String joinQuery;
		StringBuilder joinStringBuilder = new StringBuilder();
		joinStringBuilder.append("{!join from=");
		joinStringBuilder.append(from);
		joinStringBuilder.append(" to=");
		joinStringBuilder.append(to);
		joinStringBuilder.append(" fromIndex=");
		joinStringBuilder.append(fromIndex + "}");
		joinStringBuilder.append(query);
		joinQuery = joinStringBuilder.toString();
		return joinQuery;
	}

	/*
	 * This is a skeleton prototype for the multi boolean entity queries
	 * 
	 * public static String getQueryString( String entityId, String entityType,
	 * String entityAttribute, String entityAttributeValue, SearchResponse
	 * searchResponse ) { String query; String baseQuery=""; EntityClause
	 * entityClause=null; EntityTypeClause entityTypeClause; BooleanEntityQuery
	 * booleanQuery=null; if ( entityId != null ) { entityClause=new
	 * EntityClause(entityId); booleanQuery=new BooleanEntityQuery( entityClause
	 * ); } if(entityType!=null){ entityTypeClause=new EntityTypeClause(
	 * entityType,entityAttribute,entityAttributeValue ); booleanQuery=new
	 * BooleanEntityQuery( entityTypeClause ); }
	 * 
	 * query=booleanQuery.getBooleanEntityQuery( searchResponse ); return query;
	 * }
	 */
}
