package com.gcaa.status.metrics;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatusMetricsCollectorApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
    	SpringApplication.run(StatusMetricsCollectorApplication.class, args);
    }
    
	@Override
	public void run(String... args) throws Exception {
	}
}
