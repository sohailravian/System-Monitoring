package com.gcaa.metrics.domain.model;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = 3726478060086799463L;
	private long id;	
	private String code;
	private String description;
	
	protected Category() {}
	
	public Category(long id,String code,String description) {
		this.code = code;
		this.description =	description;
	}
	
	protected Category(String code,String description) {
		this.code = code;
		this.description =	description;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
