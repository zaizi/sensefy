package com.zaizi.sensefy.dataprocessing.mico.admin;

public class MicoItem {

	private String micoUri;
	private String solrId;
	private ContentType contentType;


	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getMicoUri() {
		return micoUri;
	}

	public void setMicoUri(String micoUri) {
		this.micoUri = micoUri;
	}

	public String getSolrId() {
		return solrId;
	}

	public void setSolrId(String solrId) {
		this.solrId = solrId;
	}
}
