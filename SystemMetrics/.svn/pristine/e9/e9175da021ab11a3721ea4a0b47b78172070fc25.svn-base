package com.gcaa.metrics.domain.model;

public abstract class Resource {
	
	private double used;
	private double total;
	
	public Resource(double used, double total) {
		this.used = used;
		this.total = total;
	}
	public double getUsed() {
		return used;
	}
	public void setUsed(double used) {
		this.used = used;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "Memory [used=" + used + ", free=" + (total - used) + " , total=" + total + "]";
	}
}
