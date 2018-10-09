package com.gcaa.metrics.domain.model;

import java.util.Date;

public class Utilization {
	
	private long id;
	private Host host;
	private Type type;
	private Category category;
	private String info;
	private Resource memory;
	private Date dateTime;
	
	protected Utilization(long id, Host host, Type type, Category category, Resource memory, Date dateTime) {
		super();
		this.id = id;
		this.host = host;
		this.type = type;
		this.category = category;
		this.setMemory(memory);
		this.dateTime = dateTime;
	}
	
	public Utilization(Host host, Type type, Category category, Resource memory, Date dateTime) {
		super();
		this.host = host;
		this.type = type;
		this.category = category;
		this.setMemory(memory);
		this.dateTime = dateTime;
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
	public Resource getMemory() {
		return memory;
	}
	public void setMemory(Resource memory) {
		this.memory = memory;
	}

}
