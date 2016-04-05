package com.zaizi.sensefy.dataprocessing.search.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrSearchService {
	
	private static final Logger LOG = Logger.getLogger(SolrSearchService.class);
	
	protected static final String SOLR_PRIMARY_CORE = "primaryIndex";

	protected static final String SOLR_ENTITY_CORE = "entity";

	protected static final String SOLR_ENTITY_TYPE_CORE = "entityType";

	protected Map<String, SolrClient> core2SolrClient;

	@Value("${solr.baseendpoint}")
	protected String solrBase;

	@Value("${solr.cloud}")
	protected boolean solrCloudEnabled;

	@Value("${solr.zkEnsemble}")
	protected String zkEnsemble;

	public SolrSearchService() {
		core2SolrClient = new HashMap<>();
	}

	/* Getter and setter */

	public SolrClient getCore(String name) {
		return this.core2SolrClient.get(name);
	}

	public void setPrimaryIndexCore(SolrClient primaryIndex) {
		this.core2SolrClient.put(SOLR_PRIMARY_CORE, primaryIndex);
	}
	
	public void setEntityCore(SolrClient entityCore) {
		this.core2SolrClient.put(SOLR_ENTITY_CORE, entityCore);
	}

	public void setEntityTypeCore(SolrClient entityTypeCore) {
		this.core2SolrClient.put(SOLR_ENTITY_TYPE_CORE, entityTypeCore);
	}

	public SolrClient getPrimaryIndex() {
		return this.core2SolrClient.get(SOLR_PRIMARY_CORE);
	}

	public SolrClient getEntityCore() {
		return this.core2SolrClient.get(SOLR_ENTITY_CORE);
	}

	public SolrClient getEntityTypeCore() {
		return this.core2SolrClient.get(SOLR_ENTITY_TYPE_CORE);
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
	public void init(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Initializing Solr AutoComplete Service...");
		}
		if (!solrCloudEnabled) {
			setPrimaryIndexCore(new HttpSolrClient(solrBase + "/" + SOLR_PRIMARY_CORE));
			
			setEntityTypeCore(new HttpSolrClient(solrBase + "/" + SOLR_ENTITY_TYPE_CORE));
		} else {
			try {
				CloudSolrClient cloudPrimaryServer = new CloudSolrClient(zkEnsemble);
				cloudPrimaryServer.setDefaultCollection(SOLR_PRIMARY_CORE);
				setPrimaryIndexCore(cloudPrimaryServer);

				CloudSolrClient cloudEntityServer = new CloudSolrClient(zkEnsemble);
				cloudEntityServer.setDefaultCollection(SOLR_ENTITY_CORE);
				setEntityCore(cloudEntityServer);

				CloudSolrClient cloudEntityTypesServer = new CloudSolrClient(zkEnsemble);
				cloudEntityTypesServer.setDefaultCollection(SOLR_ENTITY_TYPE_CORE);
				setEntityTypeCore(cloudEntityTypesServer);
			} catch (Exception e) {
				LOG.error("Failed to initialize the Search API server in Solr Cloud mode", e);
			}
		}
	}

}
