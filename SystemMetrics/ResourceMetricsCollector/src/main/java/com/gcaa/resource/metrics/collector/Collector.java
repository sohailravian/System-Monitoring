package com.gcaa.resource.metrics.collector;

import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;

import com.gcaa.metrics.domain.model.Resource;

import oshi.SystemInfo;

public interface Collector {
	
	public static int HUNDRED_PERCENT=100;
	public static final String WINDOWS = "Window";
	public Optional<Resource> collect();
	public Optional<Resource> collectByProcessId(int processId);
	
	public default double cpuConsumption(OperatingSystemMXBean operatingSystemMXBean,SystemInfo systemInfo) {
		double used = 0 ;
			used = systemInfo.getHardware().getProcessor().getSystemCpuLoad() * HUNDRED_PERCENT; 
		return used;
	}
	
	public default boolean isWindows(SystemInfo systemInfo) {
		if(systemInfo.getOperatingSystem().getFamily().contains(WINDOWS))
			return true;
		return false;
	}
	
}
