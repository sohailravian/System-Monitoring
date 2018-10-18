package com.gcaa.service.metrics.config;

import java.util.ArrayList;
import java.util.List;

public class CollectorProperties {

	private String frequencyCron;
	private String endpoint;
	private String name;
	private List<String> measure = new ArrayList<>();
	private String type;
	private String category;
	
	public List<String> getMeasure() {
		return measure;
	}

	public void setMeasure(List<String> measure) {
		this.measure = measure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrequencyCron() {
		return frequencyCron;
	}

	public void setFrequencyCron(String frequencyCron) {
		this.frequencyCron = frequencyCron;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
