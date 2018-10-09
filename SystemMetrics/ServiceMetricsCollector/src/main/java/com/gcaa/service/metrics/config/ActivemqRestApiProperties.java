package com.gcaa.service.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="activemq.restapi")
public class ActivemqRestApiProperties {

	private String username;
	private String password;
	private String host;
	private String readEndpoint;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getReadEndpoint() {
		return readEndpoint;
	}
	public void setReadEndpoint(String readEndpoint) {
		this.readEndpoint = readEndpoint;
	}
	

	
}
