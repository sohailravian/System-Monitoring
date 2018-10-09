package com.gcaa.service.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queue")
public class QueueCollectorProperties extends CollectorProperties {
	
}
