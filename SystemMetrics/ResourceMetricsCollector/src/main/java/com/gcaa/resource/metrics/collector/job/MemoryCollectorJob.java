package com.gcaa.resource.metrics.collector.job;

import static com.gcaa.metrics.domain.common.util.NumberUtils.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.common.util.FileUtils;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Memory;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.model.Utilization;
import com.gcaa.resource.metrics.collector.MemoryCollector;
import com.gcaa.resource.metrics.config.MemoryCollectorProperties;
import com.gcaa.resource.metrics.service.ApplicationService;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem.ProcessSort;

@Component
public class MemoryCollectorJob extends CollectorJob {
	
	private ApplicationService applicationService;
	private MemoryCollectorProperties memoryProperties;
	private MemoryCollector memoryCollector;
	
	@Autowired
	private SystemInfo systemInfo;
	
	private static Logger LOGGER = LoggerFactory.getLogger(MemoryCollectorJob.class);
	
	@Autowired
	public MemoryCollectorJob(ApplicationService applicationService, MemoryCollectorProperties memoryProperties, MemoryCollector memoryCollector) {
		this.applicationService = applicationService;
		this.memoryProperties	= memoryProperties; 
		this.memoryCollector	= memoryCollector;
	}
	
	@PostConstruct
	public void postInitilization() {
		this.setType(getCommonApplicationService().getTypeLookupByCode(memoryProperties.getType()).get());
		this.setCategory(getCommonApplicationService().getCategoryLookupByCode(memoryProperties.getCategory()).get());
	}
	
	@Scheduled(cron = "${memory.frequency-cron}")
	public void collectScheduledMemoryUtilization() {
		
		LOGGER.info("{ Memory collection job started }");
		new Thread(() -> collectMemoryForTopProcesses()).start();
		new Thread(() -> collectMemoryForProcesses()).start();
		collectSystemMemory();
		LOGGER.info("{ Memory collection job started }");
	}
	
	public void collectMemoryForProcesses(){
		
		if(!memoryProperties.getProcessIds().isEmpty()){
			
			List<Utilization> UtilizationList = new ArrayList<Utilization>();
			double totalMemory = Double.valueOf(systemInfo.getHardware().getMemory().getTotal()) / Double.valueOf(memoryProperties.getUnit());
			
			memoryProperties.getProcessIds().forEach(process -> {
					Optional<Integer> processId = FileUtils.getProcessIdByFilePath(process.getFilePath());
					
					if(processId.isPresent()) {
						LOGGER.info(" { Process id {" + processId.get() +" } found with path { " + process.getFilePath() + " }}");
						Optional<Resource> memory= memoryCollector.collectByProcessId(processId.get());
						if(memory.isPresent()) {
							
							Category category = getCommonApplicationService().getCategoryLookupByCode(process.getCategory()).get();
							
							memory.get().setTotal(doubleFormatter(totalMemory));
							Utilization utilization = utilization(this.getType(), this.getCategory(), category.getDescription() + " | "+ processId.get(), category.getDescription(), memory.get());
							UtilizationList.add(utilization);
						}
					}
				});
			
				applicationService.saveUtilizationInBatch(UtilizationList);
		};
	}
	
	public void collectMemoryForTopProcesses(){
		
		List<Utilization> UtilizationList = new ArrayList<Utilization>();
		double totalMemory = Double.valueOf(systemInfo.getHardware().getMemory().getTotal()) / Double.valueOf(memoryProperties.getUnit());
		OSProcess[] osProcesses = systemInfo.getOperatingSystem().getProcesses(memoryProperties.getProcessCount(), ProcessSort.MEMORY);
		
		Arrays.stream(osProcesses).forEach(process -> {
				double used	= (double)(process.getResidentSetSize()) / (double)memoryProperties.getUnit();
				double total = totalMemory;
				Memory memory =  new Memory(doubleFormatter(used), doubleFormatter(total));
				String name = process.getUser();
				if(!memoryCollector.isWindows(systemInfo)) {
					name = memoryCollector.javaProcessName(process);
				}
				
				Utilization utilization = utilization(this.getType(), this.getCategory(), (name + " | "+ process.getProcessID()),name, memory);
				UtilizationList.add(utilization);
			});
			
		applicationService.saveUtilizationInBatch(UtilizationList);
	}

	
	public void collectSystemMemory(){
		
		Type type = getCommonApplicationService().getTypeLookupByCode(memoryProperties.getType()).get();
		Category category = getCommonApplicationService().getCategoryLookupByCode(memoryProperties.getCategory()).get();
		
		Optional<Resource> memory= memoryCollector.collect();
		Utilization utilization = utilization(type, category, type.getDescription(),type.getDescription(), memory.get());
		applicationService.saveUtilization(utilization);
	}
	
}
