package com.gcaa.metrics.domain.model;

import java.util.Date;

public class Metrics {
	
	private long id;
	private Host host;
	private Type type;
	private Category category;
	private Measurement measurement;
	private String info;
	private Date dateTime;
	private long value;
	
	protected Metrics(long id, Host host, Type type, Category category, Measurement measurement, Date dateTime,
			long value) {
		super();
		this.id = id;
		this.host = host;
		this.type = type;
		this.category = category;
		this.measurement = measurement;
		this.dateTime = dateTime;
		this.value = value;
	}
	
	public Metrics(Host host, Type type, Category category, Measurement measurement, Date dateTime,
			long value) {
		super();
		this.host = host;
		this.type = type;
		this.category = category;
		this.measurement = measurement;
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
	public Measurement getMeasurement() {
		return measurement;
	}
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public long isValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	
}