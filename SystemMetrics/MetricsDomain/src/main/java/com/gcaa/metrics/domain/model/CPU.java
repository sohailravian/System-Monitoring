package com.gcaa.metrics.domain.model;

public class CPU extends Resource {
	
	private String name;

	public CPU(double used, double total) {
		super(used, total);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CPU [name=" + name + "]";
	}
	
	
	
}
