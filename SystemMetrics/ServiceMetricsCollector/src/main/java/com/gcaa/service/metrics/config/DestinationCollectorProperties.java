package com.gcaa.service.metrics.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "destination")
public class DestinationCollectorProperties extends CollectorProperties {

	private List<String> measure = new ArrayList<>();

	public List<String> getMeasure() {
		return measure;
	}

	public void setMeasure(List<String> measure) {
		this.measure = measure;
	}

}
