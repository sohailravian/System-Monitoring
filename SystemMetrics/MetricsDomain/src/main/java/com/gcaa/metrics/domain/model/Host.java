package com.gcaa.metrics.domain.model;

public class Host {
	
	private int id;
	private String name;
	private String ip;
	
	public Host(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	protected Host(String name,String ip) {
		super();
		this.name = name;
		this.ip = ip;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
