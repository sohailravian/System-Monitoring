package com.gcaa.resource.metrics.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "disk")
public class DiskSpaceCollectorProperties extends CollectorProperties {
	
	private long unit;
	private List<MountPoint> mountPoints = new ArrayList<MountPoint>();
	
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
	
	public List<MountPoint> getMountPoints() {
		return mountPoints;
	}
	public void setMountPoints(List<MountPoint> mountPoints) {
		this.mountPoints = mountPoints;
	}
	
	public static class MountPoint{
		
		private String name;
		private String path;
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
