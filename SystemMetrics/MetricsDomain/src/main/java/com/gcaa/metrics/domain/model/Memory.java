package com.gcaa.metrics.domain.model;

public class Memory extends Resource {

	public Memory(double used, double total) {
		super(used, total);
	}

	@Override
	public String toString() {
		return "Memory [toString() of =" + super.toString() + "]";
	}
	
	
	
}
