package com.gcaa.service.metrics;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableJms
public class ServiceCollectorApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceCollectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}
	
}
