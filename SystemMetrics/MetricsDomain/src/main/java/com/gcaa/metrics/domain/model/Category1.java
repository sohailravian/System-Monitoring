package com.gcaa.metrics.domain.model;

import java.util.Arrays;

public enum Category1 {
	
	MEMORY(1,"CAT001"),
	DISK(2,"CAT002"),
	CPU(3,"CAT003"),
	RIE(4,"CAT004"),
	RFE(5,"CAT005"),
	BROKER(6,"CAT006"),
	QUEUE(7,"CAT007"),
	TOPIC(8,"CAT008"),
	DATABASE(9,"CAT009"),
	RIE_WEB(10,"CAT010"),
	RIE_SOAP(11,"CAT011"),
	PSI(12,"CAT012"),
	MAIL_SERVER(13,"CAT013"),
	UN_RESOLVED(100,"CAT100");
	
	private Category1(int id,String code) {
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
	
	public static Category1 categoryByCode(String code) {
		return Arrays.stream(Category1.values()).
				filter(category -> code.equalsIgnoreCase(category.getCode())).findFirst().orElse(UN_RESOLVED);
	}
	
	public static void main(String[] args) {
		System.out.println(Category1.categoryByCode("cat004"));
	}
	
}
