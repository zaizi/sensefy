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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import org.zaizi.sensefy.api.dto.response.ResponseError;
import org.zaizi.sensefy.api.exception.ComponentCode;
import org.zaizi.sensefy.api.exception.SensefyException;

/**
 * This class represents a generic Solr Service, with the initialization of the
 * different Solr Cores needed by the application
 * 
 * @Author: Alessandro Benedetti Date: 01/07/2014
 * @since 1.4 <b><i>Modified the code to remove authentication and ACL from
 *        previous (@since 1.4) implementation </i></b>
 */
@Configuration
@PropertySource(value={"classpath:/solr.properties"})
public class SolrService {
	private static final Logger logger = LoggerFactory.getLogger(SolrService.class);

	/* Entity index */
	protected static final String ID_FIELD = "id";

	/* Index Names */
	protected static final String SOLR_PRIMARY_CORE = "primaryIndex";

	protected static final String SOLR_ANALYTICS_CORE = "primaryIndex_analytics";

	protected static final String SOLR_ENTITY_CORE = "entity";

	protected static final String SOLR_ENTITY_TYPE_CORE = "entityType";

	protected Map<String, SolrServer> core2SolrServer;

	@Value("${sensefy.search.solr.baseendpoint}")
	protected String solrBase;

	@Value("${sensefy.search.solr.cloud}")
	protected boolean solrCloudEnabled;

	@Value("${sensefy.search.solr.zkEnsemble}")
	protected String zkEnsemble;

	public SolrService() {
		core2SolrServer = new HashMap<>();
	}

	public SolrService(String solrBase) {
		this.solrBase = solrBase;
	}

	/* Getter and setter */

	public SolrServer getCore(String name) {
		return this.core2SolrServer.get(name);
	}

	public void setPrimaryIndexCore(SolrServer primaryIndex) {
		this.core2SolrServer.put(SOLR_PRIMARY_CORE, primaryIndex);
	}

	public void setAnalyticsCore(SolrServer analyticsCore) {
		this.core2SolrServer.put(SOLR_ANALYTICS_CORE, analyticsCore);
	}

	public void setEntityCore(SolrServer entityCore) {
		this.core2SolrServer.put(SOLR_ENTITY_CORE, entityCore);
	}

	public void setEntityTypeCore(SolrServer entityTypeCore) {
		this.core2SolrServer.put(SOLR_ENTITY_TYPE_CORE, entityTypeCore);
	}

	public SolrServer getPrimaryIndex() {
		return this.core2SolrServer.get(SOLR_PRIMARY_CORE);
	}

	public SolrServer getAnalyticsIndex() {
		return this.core2SolrServer.get(SOLR_ANALYTICS_CORE);
	}

	public SolrServer getEntityCore() {
		return this.core2SolrServer.get(SOLR_ENTITY_CORE);
	}

	public SolrServer getEntityTypeCore() {
		return this.core2SolrServer.get(SOLR_ENTITY_TYPE_CORE);
	}

	public String getZkEnsemble() {
		return zkEnsemble;
	}

	public void setZkEnsemble(String zkEnsemble) {
		this.zkEnsemble = zkEnsemble;
	}

	public boolean isSolrCloudEnabled() {
		return solrCloudEnabled;
	}

	public void setSolrCloudEnabled(boolean solrCloudEnabled) {
		this.solrCloudEnabled = solrCloudEnabled;
	}

	/**
	 * Initialization of Solr based service, all the 3 Semantic solr Cores are
	 * configured here
	 */
	@PostConstruct
	public void init() throws SensefyException {
		int processors = Runtime.getRuntime().availableProcessors();
		if (logger.isDebugEnabled()) {
			logger.debug("Initializing Solr AutoComplete Service...");
		}
		if (!solrCloudEnabled) {
			setPrimaryIndexCore(new HttpSolrServer(solrBase + "/" + SOLR_PRIMARY_CORE));
			setAnalyticsCore(
					new ConcurrentUpdateSolrServer(solrBase + "/" + SOLR_ANALYTICS_CORE, processors, processors));
			setEntityCore(new HttpSolrServer(solrBase + "/" + SOLR_ENTITY_CORE));
			setEntityTypeCore(new HttpSolrServer(solrBase + "/" + SOLR_ENTITY_TYPE_CORE));
		} else {
			try {
				CloudSolrServer cloudPrimaryServer = new CloudSolrServer(zkEnsemble);
				cloudPrimaryServer.setDefaultCollection(SOLR_PRIMARY_CORE);
				setPrimaryIndexCore(cloudPrimaryServer);

				CloudSolrServer cloudAnalyticsServer = new CloudSolrServer(zkEnsemble);
				cloudAnalyticsServer.setDefaultCollection(SOLR_ANALYTICS_CORE);
				setAnalyticsCore(cloudAnalyticsServer);

				CloudSolrServer cloudEntityServer = new CloudSolrServer(zkEnsemble);
				cloudEntityServer.setDefaultCollection(SOLR_ENTITY_CORE);
				setEntityCore(cloudEntityServer);

				CloudSolrServer cloudEntityTypesServer = new CloudSolrServer(zkEnsemble);
				cloudEntityTypesServer.setDefaultCollection(SOLR_ENTITY_TYPE_CORE);
				setEntityTypeCore(cloudEntityTypesServer);
			} catch (Exception e) {
				logger.error("Failed to initialize the Search API server in Solr Cloud mode", e);
				throw new SensefyException(500, "Failed to initialize the Search API server in Solr Cloud mode"); // To
				// Templates.
			}
		}
	}

	/**
	 * Simply process the exception received
	 *
	 * @param response
	 * @param e
	 * @param message
	 */
	protected void processAPIException(AbstractSensefyResponse response, Exception e, String message, int statusCode,
			ComponentCode componentCode) {
		logger.error(message, e);
		ResponseError error = new ResponseError(statusCode, componentCode, e.getMessage());
		response.setError(error);
		response.setStatus(statusCode);
	}

}
