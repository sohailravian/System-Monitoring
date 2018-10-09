package com.gcaa.service.metrics.model.broker;

import com.fasterxml.jackson.databind.JsonNode;

public class Queue {
	private JsonNode value;

	Queue() {}

	public JsonNode getValue() {
		return value;
	}
	public void setValue(JsonNode value) {
		this.value = value;
	}
	


}
