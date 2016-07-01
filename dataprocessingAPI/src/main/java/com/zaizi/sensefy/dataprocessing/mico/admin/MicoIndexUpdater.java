package com.zaizi.sensefy.dataprocessing.mico.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zaizi.mico.client.StatusChecker;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.status.impl.StatusResponse;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import com.zaizi.sensefy.dataprocessing.mico.dbpedia.DbpediaQueryClient;
import com.zaizi.sensefy.dataprocessing.search.service.SolrSearchService;

public class MicoIndexUpdater {

	private static final Logger LOG = Logger.getLogger(MicoIndexUpdater.class);

	private static final String CHECK_UPDATED_QUERY = "is_processed_mico:false";
	private static final String ID_FIELD = "id";
	private static final String MICO_URI_FIELD = "mico_uri";
	private static final String MICO_PROSSESED_FIELD = "is_processed_mico";
	private static final String MIMETYPE = "mimetype";
	private static final String LANG = "language";
	private static final String DOC_IDS_FIELD = "doc_ids";
	private static final String THUMBNAIL_FIELD = "thumbnail";
	private static final String DESCRIPTION_FIELD = "description";
	private static final String LATITUDE_FIELD ="lat";
	private static final String LONGITUDE_FIELD = "long";
	private static final String LABELS_FIELD = "label";
	private static final String TYPE_FIELD = "type";
	private static final String IS_PERSON_FIELD = "is_person";
	private static final String IS_PLACE_FIELD = "is_place";
	private static final String IS_ORGANISATION_FIELD = "is_organization";
	private static final String IS_OTHER_FIELD = "is_other";
	
	private static final String IS_PLACE_ONT = "http://dbpedia.org/ontology/Place";
	private static final String IS_PERSON_ONT = "http://dbpedia.org/ontology/Person";
	private static final String IS_ORGANISATION_ONT = "http://dbpedia.org/ontology/Organisation";

	@Autowired
	private SolrSearchService solrSearchService;
	
	@Autowired
	private QueryClient queryClient;
	
	@Autowired
	private DbpediaQueryClient dbpediaQueryClient;
	
	@Autowired
	private StatusChecker statusChecker;

	public void updateSolrIndex(){
		LOG.info("Running Indexupdate Job....");
		SolrClient primaryIndexClient = solrSearchService.getPrimaryIndex();
		SolrClient entityClient = solrSearchService.getEntityCore();
		SolrQuery solrQuery = new SolrQuery(CHECK_UPDATED_QUERY);
		List<MicoItem> micoItems = queryDocuments(primaryIndexClient, solrQuery);
		try{
			for (MicoItem micoItem : micoItems) {
				List<StatusResponse> statusResponses = statusChecker.checkItemStatus(micoItem.getMicoUri(), true);
				if (!statusResponses.isEmpty())
	            {
	                StatusResponse statusResponse = statusResponses.get(0);
	                if (!statusResponse.isFinished()){
	                	continue;
	                }
	            }
				
				SolrInputDocument primaryDoc = new SolrInputDocument();
				primaryDoc.addField(ID_FIELD, micoItem.getSolrId());
				
				List<SolrInputDocument> entityDocs;
				
				switch (micoItem.getContentType()) {
				case IMAGE:
					
					break;
				case VIDEO:
					entityDocs = addNamedEntities(micoItem, primaryDoc);
					if(!entityDocs.isEmpty()){
						entityClient.add(entityDocs);
					}
					break;
				case TEXT:
					entityDocs = addNamedEntities(micoItem, primaryDoc);
					if(!entityDocs.isEmpty()){
						entityClient.add(entityDocs);
					}
					break;
				default:
					break;
				}
				
				// set primary index field is_processed_mico to true
				Map<String, Object> fieldModifier = new HashMap<String, Object>();
				fieldModifier.put("set", true);
				primaryDoc.addField(MICO_PROSSESED_FIELD, fieldModifier);
				primaryDoc.setField(LANG, micoItem.getLanguage());
				primaryIndexClient.add(primaryDoc);
			}
			
			primaryIndexClient.commit(true,true);
			entityClient.commit(true,true);
		}catch(SolrServerException e){
			LOG.error("Solr Server Error", e);
		} catch (IOException e) {
			LOG.error("Solr Server Error", e);
		} catch (MicoClientException e) {
			LOG.error("Error is checking item status in mico platform", e);
		}
		
	}
	
	private List<SolrInputDocument> addNamedEntities(MicoItem micoItem, SolrInputDocument primaryDoc){
		List<SolrInputDocument> entityDocs = new ArrayList<>();
		Set<String> smltEntities = new HashSet<>();
		try {
			List<LinkedEntity> entities = queryClient.getLinkedEntities(micoItem.getMicoUri());
			Set<LinkedEntity> entitySet = new HashSet<>(entities);
			entitySet.forEach(linkedEntity -> {
				SolrInputDocument entityDoc = new SolrInputDocument();
				smltEntities.add(linkedEntity.getEntityReference());
				entityDoc.addField(ID_FIELD, linkedEntity.getEntityReference());
				entityDoc.addField(DOC_IDS_FIELD, micoItem.getSolrId());
				entityDoc.addField(LABELS_FIELD, linkedEntity.getEntityLabel());
				ResultSet results = dbpediaQueryClient.getEntityResults(linkedEntity.getEntityReference());
				while(results.hasNext()){
					QuerySolution soln = results.nextSolution();
					Literal literal = null;
					String[] types = null;
					if(soln.get("?typeList").isLiteral()){
						types = soln.getLiteral("?typeList").getString().split("###");
					}
					if(soln.get("?typeList").isResource()){
						types = soln.getResource("?typeList").toString().split("###");
					}
					List<String> typesList = Arrays.asList(types);
					Set<String> typesSet = new HashSet<>(typesList);
					
					if(soln.get("?comment").isLiteral()){
						literal = soln.getLiteral("?comment"); 
					}
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
					entityDoc.addField(TYPE_FIELD, typesList);
					if(typesSet.contains(IS_PERSON_ONT)){
						entityDoc.addField(IS_PERSON_FIELD, true);
					}
					else if(typesSet.contains(IS_PLACE_ONT)){
						entityDoc.addField(IS_PLACE_FIELD, true);
					}
					else if(typesSet.contains(IS_ORGANISATION_ONT)){
						entityDoc.addField(IS_ORGANISATION_FIELD, true);
					}
					else{
						entityDoc.addField(IS_OTHER_FIELD, true);
					}
					
					break;
				}
				entityDocs.add(entityDoc);
			});
			primaryDoc.addField("smlt_entities", smltEntities);
		} catch (MicoClientException e) {
			LOG.error("Error in quering mico platform", e);
		}
		return entityDocs;
	}
	
	public List<MicoItem> queryDocuments(SolrClient client, SolrQuery solrQuery) {
		List<MicoItem> micoItems = new ArrayList<>();
		solrQuery.setSort(SortClause.asc(ID_FIELD));
		solrQuery.set("fl", ID_FIELD+","+MICO_URI_FIELD+","+MIMETYPE+","+LANG);
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
					String lang = (String)solrDocument.getFieldValue(LANG);
					if(mimetype == null){
						mimetype = "";
					}
					String[] types = mimetype.split("/");
					String baseType = types[0];
					MicoItem micoItem = new MicoItem();
					micoItem.setSolrId(id);
					micoItem.setMicoUri(micoUri);
					micoItem.setLanguage(lang);
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
