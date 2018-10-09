package com.gcaa.resource.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cpu")
public class PerformanceCollectorProperties extends CollectorProperties {
	
}
