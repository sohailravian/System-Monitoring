package com.gcaa.metrics.domain.model;

import java.util.Arrays;

public enum Measurement1 {
	
	NO_OF_CONSUMBER(1,"MSM001"),
	NO_OF_QUEUES(2,"MSM002"),
	NO_OF_TOPICS(3,"MSM003"),
	NO_OF_MESSAGES(4,"MSM004"),
	NO_OF_MESSAGES_ENQUEUED(5,"MSM005"),
	NO_OF_MESSAGES_DEQUEUED(6,"MSM006"),
	MEMORY_USAGE_BYTE(7,"MSM007"),
	UPSTREAM_SERVER(8,"MSM008"),
	STATUS(9,"MSM009"),
	UN_RESOLVED(-20,"MSM-20");
	
	private Measurement1(int id,String code) {
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
	
	public static Measurement1 measurementByCode(String code) {
		return Arrays.stream(Measurement1.values()).
				filter(measure -> code.equalsIgnoreCase(measure.getCode())).findFirst().orElse(UN_RESOLVED);
	}
}
