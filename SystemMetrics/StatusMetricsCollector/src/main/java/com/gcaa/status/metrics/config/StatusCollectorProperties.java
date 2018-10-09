package com.gcaa.status.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "status")
public class StatusCollectorProperties extends CollectorProperties {
	
	private long unit;
	
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
}
