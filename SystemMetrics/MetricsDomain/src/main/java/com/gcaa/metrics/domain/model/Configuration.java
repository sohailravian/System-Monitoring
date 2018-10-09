package com.gcaa.metrics.domain.model;

import java.util.Date;

public class Configuration {

	private int id;
	private Type type;
	private Category category;
	private int warningThreshold;
	private int criticalThreshold;
	private boolean active;
	private Date dateTime;
	
	protected Configuration(int id, Type type, Category category, boolean active, Date dateTime) {
		super();
		this.id = id;
		this.type = type;
		this.category = category;
		this.active = active;
		this.dateTime = dateTime;
	}
	
	public Configuration(Type type, Category category, boolean active, Date dateTime) {
		super();
		this.type = type;
		this.category = category;
		this.active = active;
		this.dateTime = dateTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getWarningThreshold() {
		return warningThreshold;
	}
	public void setWarningThreshold(int warningThreshold) {
		this.warningThreshold = warningThreshold;
	}
	public int getCriticalThreshold() {
		return criticalThreshold;
	}
	public void setCriticalThreshold(int criticalThreshold) {
		this.criticalThreshold = criticalThreshold;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
