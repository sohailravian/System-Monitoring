package com.gcaa.metrics.domain.model;

import java.util.Arrays;

public enum Category {
	
	MEMORY(1,"CAT001"),
	DISK(2,"CAT002"),
	CPU(3,"CAT003"),
	RIE(4,"CAT004"),
	RFE(5,"CAT005"),
	BROKER(6,"CAT006"),
	QUEUE(7,"CAT007"),
	TOPIC(8,"CAT008"),
	UN_RESOLVED(100,"CAT100");
	
	private Category(int id,String code) {
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
	
	public static Category categoryByCode(String code) {
		return Arrays.stream(Category.values()).
				filter(category -> code.equalsIgnoreCase(category.getCode())).findFirst().orElse(UN_RESOLVED);
	}
	
	public static void main(String[] args) {
		System.out.println(Category.categoryByCode("cat004"));
	}
	
}
