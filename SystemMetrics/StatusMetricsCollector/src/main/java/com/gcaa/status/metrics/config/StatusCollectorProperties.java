package com.gcaa.status.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "status")
public class StatusCollectorProperties extends CollectorProperties {
	
	private long unit;
	private UpstreamServer upstreamServer;
	
	public UpstreamServer getUpstreamServer() {
		return upstreamServer;
	}
	public void setUpstreamServer(UpstreamServer upstreamServer) {
		this.upstreamServer = upstreamServer;
	}
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
	
	public static class UpstreamServer{
		
		private String category;
		private String type;
		private String measurement;
		private String directoryPath;
		private String extension;
		
		public String getDirectoryPath() {
			return directoryPath;
		}
		public void setDirectoryPath(String directoryPath) {
			this.directoryPath = directoryPath;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getExtension() {
			return extension;
		}
		public void setExtension(String extension) {
			this.extension = extension;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getMeasurement() {
			return measurement;
		}
		public void setMeasurement(String measurement) {
			this.measurement = measurement;
		}
		
	} 
	
}
