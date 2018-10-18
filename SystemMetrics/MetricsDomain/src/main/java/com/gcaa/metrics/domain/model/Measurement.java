package com.gcaa.metrics.domain.model;

import java.io.Serializable;

public class Measurement implements Serializable {

	private static final long serialVersionUID = 3069565714420969735L;
	
	private long id;	
	private String code;
	private String description;
	
	protected Measurement() {}
	
	public Measurement(long id,String code,String description) {
		this.code = code;
		this.description =	description;
	}
	
	protected Measurement(String code,String description) {
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
