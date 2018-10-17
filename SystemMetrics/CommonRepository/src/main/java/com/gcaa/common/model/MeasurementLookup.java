package com.gcaa.common.model;

import java.io.Serializable;

public class MeasurementLookup implements Serializable {

	private static final long serialVersionUID = 3069565714420969735L;
	
	private long id;	
	private String code;
	private String description;
	
	protected MeasurementLookup() {}
	
	public MeasurementLookup(long id,String code,String description) {
		this.code = code;
		this.description =	description;
	}
	
	protected MeasurementLookup(String code,String description) {
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
