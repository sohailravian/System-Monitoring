package com.gcaa.metrics.domain.model;

import java.io.Serializable;

public class Type implements Serializable {

	private static final long serialVersionUID = 6391589797888243895L;
	private long id;	
	private String code;
	private String description;
	
	protected Type() {}
	
	public Type(long id,String code,String description) {
		this.code = code;
		this.description =	description;
	}
	
	protected Type(String code,String description) {
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
