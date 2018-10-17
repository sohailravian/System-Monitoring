package com.gcaa.resource.metrics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import oshi.SystemInfo;

@ComponentScan(basePackages = {"com.gcaa.common.*"})
@ImportResource(locations = {"classpath*:mapper/HostMapper.xml"})
@MapperScan(basePackages = {"com.gcaa.common.mapper","com.gcaa.resource.metrics.mapper"})
@Configuration
public class MainCollectorConfig {

	@Bean
	public SystemInfo systemInfo() {
		return new SystemInfo();
	}

}
