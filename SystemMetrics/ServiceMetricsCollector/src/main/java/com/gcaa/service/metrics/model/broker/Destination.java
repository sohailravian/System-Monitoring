package com.gcaa.service.metrics.model.broker;

public class Destination {
	
	private String objectName;
	private String brokerName;
	private String destinationName;
	private String type;
	
	Destination(){};
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	@Override
	public String toString() {
		return "Destination [objectName=" + objectName + ", brokerName=" + brokerName + ", destinationName="
				+ destinationName + ", type=" + type + "]";
	}
	
	
}
