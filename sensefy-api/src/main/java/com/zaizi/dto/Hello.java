package com.zaizi.dto;

public class Hello {

	private String id;
	private String content;
	
	

	public Hello(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
