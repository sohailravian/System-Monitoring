package com.gcaa.metrics.domain.model;

import java.util.Arrays;

public enum Type1 {
	
	SYSTEM(1,"TYP001"),
	SERVICE(2,"TYP002"),
	UN_RESOLVED(100,"TYP100"),;
	
	private Type1(int id,String code) {
		this.id=id;
		this.code=code;
	}
	
	private String code;
	private int id;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static Type1 typeByCode(String code) {
		return Arrays.stream(Type1.values()).
				filter(type -> code.equalsIgnoreCase(type.getCode())).findFirst().orElse(UN_RESOLVED);
	}
	
}
