package com.gcaa.metrics.domain.model;

import java.util.Date;

public class Configuration {

	private int id;
	private Type1 type;
	private Category1 category;
	private int warningThreshold;
	private int criticalThreshold;
	private boolean active;
	private Date dateTime;
	
	protected Configuration(int id, Type1 type, Category1 category, boolean active, Date dateTime) {
		super();
		this.id = id;
		this.type = type;
		this.category = category;
		this.active = active;
		this.dateTime = dateTime;
	}
	
	public Configuration(Type1 type, Category1 category, boolean active, Date dateTime) {
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
	public Type1 getType() {
		return type;
	}
	public void setType(Type1 type) {
		this.type = type;
	}
	public Category1 getCategory() {
		return category;
	}
	public void setCategory(Category1 category) {
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
