package com.gcaa.resource.metrics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;

@Configuration
public class MainCollectorConfig {

	@Bean
	public SystemInfo systemInfo() {
		return new SystemInfo();
	}

}
