package com.gcaa.metrics.domain.model;

import java.util.Date;

public class Status {
	
	private long id;
	private Host host;
	private Type type;
	private Category category;
	private Date dateTime;
	private boolean value;
	private String info;
	private Measurement measurement = null;
	
	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	protected Status(long id, Host host, Type type, Category category, Date dateTime, boolean value) {
		super();
		this.id = id;
		this.host = host;
		this.type = type;
		this.category = category;
		this.dateTime = dateTime;
		this.value = value;
	}
	
	public Status(Host host, Type type, Category category, Date dateTime, boolean value) {
		super();
		this.host = host;
		this.type = type;
		this.category = category;
		this.dateTime = dateTime;
		this.value = value;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
		
	
}
