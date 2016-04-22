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
	private static final String ID_FIELD = "id";
	private static final String MICO_URI_FIELD = "mico_uri";
	private static final String MICO_PROSSESED_FIELD = "is_processed_mico";
	private static final String MIMETYPE = "mimetype";
	private static final String DOC_IDS_FIELD = "doc_ids";
	private static final String THUMBNAIL_FIELD = "thumbnail";
	private static final String DESCRIPTION_FIELD = "description";
	private static final String LATITUDE_FIELD ="lat";
	private static final String LONGITUDE_FIELD = "long";
	private static final String LABELS_FIELD = "label";
	private static final String TYPE_FIELD = "type";

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
				primaryDoc.addField(ID_FIELD, micoItem.getSolrId());
				
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
				primaryDoc.addField(MICO_PROSSESED_FIELD, fieldModifier);
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
				entityDoc.addField(ID_FIELD, linkedEntity.getEntityReference());
				entityDoc.addField(DOC_IDS_FIELD, micoItem.getSolrId());
				entityDoc.addField(LABELS_FIELD, linkedEntity.getEntityLabel());
				ResultSet results = dbpediaQueryClient.getEntityResults(linkedEntity.getEntityReference());
				while(results.hasNext()){
					QuerySolution soln = results.nextSolution();
					String[] types = soln.getLiteral("?typeList").getString().split("|||");
					Literal literal = soln.getLiteral("?comment"); 
					if(literal != null){
						String description = literal.getString();
						entityDoc.addField(DESCRIPTION_FIELD, description);
					}
					literal = soln.getLiteral("?lat");
					if(literal != null){
						String lat = literal.getString();
						entityDoc.addField(LATITUDE_FIELD, lat);
						entityDoc.addField(LONGITUDE_FIELD, soln.getLiteral("?long").getString());
					}
					Resource resource = soln.getResource("?depiction");
					if(resource != null){
						entityDoc.addField(THUMBNAIL_FIELD, resource.toString());
					}
					entityDoc.addField(TYPE_FIELD, Arrays.asList(types));
					
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
		solrQuery.setSort(SortClause.asc(ID_FIELD));
		solrQuery.set("fl", ID_FIELD+","+MICO_URI_FIELD+","+MIMETYPE);
		String cursorMark = CursorMarkParams.CURSOR_MARK_START;
		try {
			boolean done = false;
			while (!done) {
				solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);
				QueryResponse response = client.query(solrQuery);
				String nextCursorMark = response.getNextCursorMark();
				List<SolrDocument> hits = response.getResults();
				for (SolrDocument solrDocument : hits) {
					String id = (String) solrDocument.getFieldValue(ID_FIELD);
					String micoUri = (String) solrDocument.getFieldValue(MICO_URI_FIELD);
					String mimetype = (String) solrDocument.getFieldValue(MIMETYPE);
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
