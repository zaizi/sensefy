package com.zaizi.sensefy.dataprocessing.mico.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CursorMarkParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.zaizi.mico.client.QueryClient;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.text.LinkedEntity;

import com.zaizi.sensefy.dataprocessing.mico.dbpedia.DbpediaQueryClient;
import com.zaizi.sensefy.dataprocessing.search.service.SolrSearchService;

public class MicoIndexUpdater {

	private static final Logger LOG = Logger.getLogger(MicoIndexUpdater.class);

	private static final String CHECK_UPDATED_QUERY = "is_processed_mico:false";

	@Autowired
	private SolrSearchService solrSearchService;
	
	@Autowired
	private QueryClient queryClient;
	
	@Autowired
	private DbpediaQueryClient dbpediaQueryClient;

	public void updateSolrIndex(){
		LOG.info("Running Indexupdate Job....");
		SolrClient primaryIndexClient = solrSearchService.getPrimaryIndex();
		SolrClient entityClient = solrSearchService.getEntityCore();
		SolrQuery solrQuery = new SolrQuery(CHECK_UPDATED_QUERY);
		List<MicoItem> micoItems = queryDocuments(primaryIndexClient, solrQuery);
		try{
			for (MicoItem micoItem : micoItems) {
				
				SolrInputDocument primaryDoc = new SolrInputDocument();
				primaryDoc.addField("id", micoItem.getSolrId());
				
				List<SolrInputDocument> entityDocs;
				
				switch (micoItem.getContentType()) {
				case IMAGE:
					
					break;
				case VIDEO:
					entityDocs = addNamedEntities(micoItem);
					entityClient.add(entityDocs);
					break;
				case TEXT:
					entityDocs = addNamedEntities(micoItem);
					entityClient.add(entityDocs);
					break;
				default:
					break;
				}
				
				// set primary index field is_processed_mico to true
				Map<String, Object> fieldModifier = new HashMap<String, Object>();
				fieldModifier.put("set", true);
				primaryDoc.addField("is_processed_mico", fieldModifier);
				primaryIndexClient.add(primaryDoc);
			}
			
			primaryIndexClient.commit(true,true);
			entityClient.commit(true,true);
			primaryIndexClient.optimize();
			entityClient.optimize();
		}catch(SolrServerException e){
			LOG.error("Solr Server Error", e);
		} catch (IOException e) {
			LOG.error("Solr Server Error", e);
		}
		
	}
	
	private List<SolrInputDocument> addNamedEntities(MicoItem micoItem){
		List<SolrInputDocument> entityDocs = new ArrayList<>();
		try {
			List<LinkedEntity> entities = queryClient.getLinkedEntities(micoItem.getMicoUri());
			for (LinkedEntity linkedEntity : entities) {
				SolrInputDocument entityDoc = new SolrInputDocument();
				entityDoc.addField("id", linkedEntity.getEntityReference());
				entityDoc.addField("doc_ids", micoItem.getSolrId());
				entityDoc.addField("label", linkedEntity.getEntityLabel());
				ResultSet results = dbpediaQueryClient.getEntityResults(linkedEntity.getEntityReference());
				while(results.hasNext()){
					QuerySolution soln = results.nextSolution();
					String[] types = soln.getLiteral("?typeList").getString().split("|||");
					Literal literal = soln.getLiteral("?comment"); 
					if(literal != null){
						String description = literal.getString();
						entityDoc.addField("description", description);
					}
					literal = soln.getLiteral("?lat");
					if(literal != null){
						String lat = literal.getString();
						entityDoc.addField("lat", lat);
						entityDoc.addField("long", soln.getLiteral("long").getString());
					}
					Resource resource = soln.getResource("?depiction");
					if(resource != null){
						entityDoc.addField("thumbnail", resource.toString());
					}
					entityDoc.addField("type", Arrays.asList(types));
					
					break;
				}
				entityDocs.add(entityDoc);
			}
		} catch (MicoClientException e) {
			LOG.error("Error in quering mico platform", e);
		}
		return entityDocs;
	}
	
	public List<MicoItem> queryDocuments(SolrClient client, SolrQuery solrQuery) {
		List<MicoItem> micoItems = new ArrayList<>();
		solrQuery.setSort(SortClause.asc("id"));
		solrQuery.set("fl", "id,mico_uri,mimetype");
		String cursorMark = CursorMarkParams.CURSOR_MARK_START;
		try {
			boolean done = false;
			while (!done) {
				solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);
				QueryResponse response = client.query(solrQuery);
				String nextCursorMark = response.getNextCursorMark();
				List<SolrDocument> hits = response.getResults();
				for (SolrDocument solrDocument : hits) {
					String id = (String) solrDocument.getFieldValue("id");
					String micoUri = (String) solrDocument.getFieldValue("mico_uri");
					String mimetype = (String) solrDocument.getFieldValue("mimetype");
					String[] types = mimetype.split("/");
					String baseType = types[0];
					MicoItem micoItem = new MicoItem();
					micoItem.setSolrId(id);
					micoItem.setMicoUri(micoUri);
					if(baseType.equals("image")){
						micoItem.setContentType(ContentType.IMAGE);
					}else if (baseType.equals("video")) {
						micoItem.setContentType(ContentType.VIDEO);
					}
					else{
						micoItem.setContentType(ContentType.TEXT);
					}
					micoItems.add(micoItem);
				}
				if (cursorMark.equals(nextCursorMark)) {
					done = true;
				}
				cursorMark = nextCursorMark;
			}
		} catch (SolrServerException e) {
			LOG.error("Error occured in quering Solr", e);
		} catch (IOException e) {
			LOG.error("Error occured in quering Solr", e);
		}

		return micoItems;
	}

}
