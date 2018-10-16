package com.gcaa.resource.metrics.collector.job;

import static com.gcaa.metrics.domain.common.util.NumberUtils.*;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gcaa.metrics.domain.common.util.FileUtils;
import com.gcaa.metrics.domain.model.CPU;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.GcaaOSProcess;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.model.Utilization;
import com.gcaa.resource.metrics.collector.PerformanceCollector;
import com.gcaa.resource.metrics.collector.TopPerformanceCollector;
import com.gcaa.resource.metrics.config.PerformanceCollectorProperties;
import com.gcaa.resource.metrics.service.ApplicationService;

import oshi.SystemInfo;

@Component
public class PerformanceCollectorJob extends CollectorJob {
	
	private ApplicationService applicationService;
	private PerformanceCollectorProperties performanceProperties;
	private PerformanceCollector performanceCollector;
	private TopPerformanceCollector topPerformanceCollector;
	
	public static transient long previousTime;
	public static transient long previousScheduleTime;
	
	public static Map<Integer,GcaaOSProcess> topProcesses = new ConcurrentHashMap<Integer,GcaaOSProcess>();
	
	@Autowired
	private SystemInfo systemInfo;
	
	private OperatingSystemMXBean operatingSystemMXBean;
	
	private static Logger LOGGER = LoggerFactory.getLogger(PerformanceCollectorJob.class);
	
	@Autowired
	public PerformanceCollectorJob(ApplicationService applicationService, 
									PerformanceCollectorProperties memoryProperties, 
									PerformanceCollector memoryCollector,
									TopPerformanceCollector topPerformanceCollector) {
		this.applicationService 		= applicationService;
		this.performanceProperties		= memoryProperties; 
		this.performanceCollector		= memoryCollector;
		this.topPerformanceCollector 	= topPerformanceCollector;
		operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}
	
	@Scheduled(cron = "${cpu.frequency-cron}")
	public void collectScheduledMemoryUtilization() {
		LOGGER.info("{ CPU collection job started }");
		
		new Thread(() -> collectPerformanceForTopProcesses()).start(); 
		new Thread(() -> collectPerformanceForSpecificProcesses()).start();
		
		collectSystemPerformance();
				
		LOGGER.info("{ CPU collection job started }");
	}
	
	public void collectPerformanceForSpecificProcesses(){
		
		List<Utilization> UtilizationList = new ArrayList<Utilization>();
		if(!performanceProperties.getProcessIds().isEmpty()){
			double totalCpuUsed = cpuConsumption(operatingSystemMXBean,systemInfo);
			performanceProperties.getProcessIds().forEach(process -> {
					
					Optional<Integer> processId = FileUtils.getProcessIdByFilePath(process.getFilePath());
					if(processId.isPresent()) {
						
						LOGGER.info("{ Process id {" + processId.get() +" } found with path { " + process.getFilePath() + " }}");
						Optional<Resource> cpu= performanceCollector.collectByProcessId(processId.get());
						if(cpu.isPresent()) {
							cpu.get().setTotal(doubleFormatter(totalCpuUsed));
							Utilization utilization = utilization(Type.SYSTEM, Category.CPU,(Category.categoryByCode(process.getName()).name()+ " | " +processId.get()),
									Category.categoryByCode(process.getName()).name(), cpu.get());
							UtilizationList.add(utilization);
						}
					}
				});
			
				applicationService.saveUtilizationInBatch(UtilizationList);
			
		};
	}
	
	public void collectPerformanceForTopProcesses(){
		
		List<Utilization> utilizationList = new ArrayList<Utilization>();
		List<Resource> resources = topPerformanceCollector.collectionTopProcess(performanceProperties.getProcessCount());
		resources.forEach(cpu -> {
			Utilization utilization = utilization(Type.SYSTEM, Category.CPU, ((CPU)cpu).getName(), ((CPU)cpu).getName().split("\\|")[0].trim(), cpu);
			utilizationList.add(utilization);
		});
		
		applicationService.saveUtilizationInBatch(utilizationList);
	}

	
	public void collectSystemPerformance(){
		Optional<Resource> memory= performanceCollector.collect();
		Utilization utilization = utilization(Type.SYSTEM, Category.CPU, Type.SYSTEM.name(),  Type.SYSTEM.name(), memory.get());
		applicationService.saveUtilization(utilization);
		
	}
	
	public static void main(String[] args) {
		String s= " ResourceMetricsCollector.jar | 8138";
		System.out.println(s.split("\\|"));
		for (String ss: s.split("\\|")) {
			System.out.println(ss);
		}
	}
		
}
