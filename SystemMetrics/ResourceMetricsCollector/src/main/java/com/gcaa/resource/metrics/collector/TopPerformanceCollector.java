package com.gcaa.resource.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.CPU;
import com.gcaa.metrics.domain.model.Resource;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem.ProcessSort;

import static com.gcaa.metrics.domain.common.util.NumberUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopPerformanceCollector implements Collector {

	private SystemInfo systemInfo ;
	
	private OperatingSystemMXBean operatingSystemMXBean;
	
	private static Logger LOGGER = LoggerFactory.getLogger(TopPerformanceCollector.class);
	
	public static Map<Integer,Long> processPreviousTime = new ConcurrentHashMap<Integer,Long>();
	
	public static Map<Integer,Long> childProcessPreviousTime = new ConcurrentHashMap<Integer,Long>();
	
	private final String UNIX_SHELL = "/bin/ps";
	private final String UNIX_COMMAND = "-eo";
	private final String PROCESS_COLUMN = "pid,user:40,%cpu";
	private final String PROCESS_SORT = "--sort=-%cpu";	
	
	
	public static transient long schdulerTime;
	public static long previousTime;
	
	@Autowired
	public TopPerformanceCollector(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
		this.operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();;
	}
	
	@Override
	public Optional<Resource> collect() {
		LOGGER.warn(" Not implemented for it");
		return Optional.empty();
	}

	@Override
	public Optional<Resource> collectByProcessId(int processId) {
		LOGGER.warn(" Not implemented for it");
		return Optional.empty();
	}
	
	public List<Resource> collectionTopProcess(int count){
		List<Resource> resources = null;
		
		LOGGER.info("{ Performance/CPU Metrics Collector Started For Top { "+count +" } Processes}");
		if(isWindows(systemInfo)) {
			resources = windowsResources(count);
		}else {
			resources = unixResources(count);
		}
		LOGGER.info("{ Performance/CPU Metrics Collector Finihsed For Top { "+count +" } Processes}");
		return resources;
	}
	
	private List<Resource> windowsResources(int count){
		
		LOGGER.info("{ Performance/CPU Metrics Collector Started For Top { "+count +" } Processes} of Windows");
		List<Resource> resources = new ArrayList<Resource>();
		double totalCpu = cpuConsumption(operatingSystemMXBean,systemInfo);
		OSProcess[] osProcesses = systemInfo.getOperatingSystem().getProcesses(count, ProcessSort.CPU);
		Arrays.stream(osProcesses).forEach(osProcess -> {
			CPU resource = new CPU(doubleFormatter(osProcess.calculateCpuPercent()), doubleFormatter(totalCpu));
			resource.setName(osProcess.getName());
			resources.add(resource);
		});
		LOGGER.info("{ Performance/CPU Metrics Collector Finished For Top { "+count +" } Processes} of Windows");
		return resources;
	}
	
	private List<Resource> unixResources(int count){
		LOGGER.info("{ Performance/CPU Metrics Collector Started For Top { "+count +" } Processes} of UNIX");
		List<Resource> resources = new ArrayList<Resource>();
		double totalCpu = cpuConsumption(operatingSystemMXBean,systemInfo);
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		Process process = null;
		
		try {
			String[] command ={UNIX_SHELL,UNIX_COMMAND,PROCESS_COLUMN,PROCESS_SORT};
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);
			process.waitFor();
			inputStreamReader = new InputStreamReader(process.getInputStream());
			reader = new BufferedReader(inputStreamReader);
			
			String line;
			int lineNumber = 0;
			while ((line = reader.readLine()) !=null && lineNumber <= count) {
				if(lineNumber == 0) {
					++lineNumber;
					continue;
				}
				
	    	 	line = line.trim().replaceAll("\\s{2,}"," ");
	    	    String[] splitLine =  Pattern.compile("\\s+").split(line);
	    	    
	    	    int processId = Integer.parseInt(splitLine[0]);
	    	    OSProcess osProcess = systemInfo.getOperatingSystem().getProcess(Integer.parseInt(splitLine[0]));
	    	    
	    	    String name = splitLine[1];
	    	    if(null != osProcess)
	    	    	name = javaProcessName(osProcess);
	    	    
	    	    CPU cpu = new CPU(doubleFormatter(Double.valueOf(splitLine[2])), doubleFormatter(totalCpu));
	    	    cpu.setName(name + " | " + processId);
	    	   
	    	    resources.add(cpu);
	    	    lineNumber++;
	    	    
	       }

		LOGGER.info("{ Performance/CPU Metrics Collector Finished For Top { "+count +" } Processes} of UNIX");
		}catch (Exception e) {
			LOGGER.error("Something wrong happened while collecting cpu metrics with unix command",e);
		}finally {
			process.destroy();
			try {
				if(null != inputStreamReader)
					inputStreamReader.close();
				if(null != reader)
					reader.close();
			}catch (Exception e) {
				LOGGER.error("Something wrong happened while trying to close the streams",e);
			}
		}
		
	
		return resources;
	}
	

	
	
	public static void main(String[] args) {
		String name = "java -jar -Dspring.config.location=/sohail/System_Monitoring_Project/Status_Metrics/application.properties /sohail/System_Monitoring_Project/Status_Metrics/StatusMetricsCollector.jar";
		Pattern pattern=Pattern.compile("[a-zA-Z0-9-_]+.jar");
		Matcher matcher = pattern.matcher(name);
		
		if(matcher.find()) {
			System.out.println(matcher.group(0));
		}
		
	}
	
}

	
