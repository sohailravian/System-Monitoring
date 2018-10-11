package com.gcaa.resource.metrics.collector;

import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gcaa.metrics.domain.model.Resource;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;

public interface Collector {
	
	public static int HUNDRED_PERCENT=100;
	public static final String WINDOWS = "Window";
	public Optional<Resource> collect();
	public Optional<Resource> collectByProcessId(int processId);
	
	public final String PROCESS_NAME = "java";
	public final String PROCESS_USER_ROOT = "root";
	
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
	
	public default String javaProcessName(OSProcess osProcess) {
		String processName =  osProcess.getUser(); 
		if(PROCESS_NAME.equalsIgnoreCase(osProcess.getName()) && PROCESS_USER_ROOT.equalsIgnoreCase(osProcess.getUser())) {
			String regExp = "[a-zA-Z0-9-_.]+.jar";
			Pattern pattern=Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(osProcess.getCommandLine());
			if(matcher.find())
				processName = matcher.group(0);
		}
		return processName;
	}
	
}
