package com.gcaa.service.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ServiceMetricsCollectionApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMetricsCollectionApplication.class, args);
	}
}
