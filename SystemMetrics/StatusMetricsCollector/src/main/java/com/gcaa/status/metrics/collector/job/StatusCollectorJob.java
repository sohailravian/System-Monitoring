package com.gcaa.status.metrics.collector.job;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gcaa.common.service.CommonApplicationService;
import com.gcaa.metrics.domain.common.util.FileUtils;
import com.gcaa.metrics.domain.common.util.RegularExpressionUtils;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Status;
import com.gcaa.status.metrics.collector.StatusCollector;
import com.gcaa.status.metrics.config.StatusCollectorProperties;
import com.gcaa.status.metrics.service.ApplicationService;
import com.gcaa.status.metrics.service.UpstreamServerService;

@Component
public class StatusCollectorJob extends CollectorJob{
	
	private ApplicationService applicationService;
	private StatusCollectorProperties statusProperties;
	private StatusCollector statusCollector;
	private UpstreamServerService fileService;
	private CommonApplicationService commonApplicationService;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollectorJob.class);
	
	@Autowired
	public StatusCollectorJob(ApplicationService applicationService, StatusCollectorProperties statusCollectorProperties, 
			StatusCollector statusCollector, UpstreamServerService fileService,CommonApplicationService commonApplicationService) {
		this.applicationService 		= applicationService;
		this.statusProperties			= statusCollectorProperties; 
		this.statusCollector			= statusCollector;
		this.fileService				= fileService;
		this.commonApplicationService 	= commonApplicationService;
	}
	
	@Scheduled(cron = "${status.frequency-cron}")
	public void collectScheduledProcessStatus() {
		LOGGER.info("{ Status collection job started }");
		collectStatusForProcesses();
		new Thread(() -> collectStatusForUpstreamServer()).start();
		LOGGER.info("{ Status collection job started }");
	}
	
	public void collectStatusForProcesses(){
		
		List<Status> statusList = new ArrayList<Status>();
		if(!statusProperties.getProcessIds().isEmpty()){
			statusProperties.getProcessIds().forEach(process -> {
					boolean processStatus = false;
					Optional<Integer> processId = FileUtils.getProcessIdByFilePath(process.getFilePath());
					if(processId.isPresent()) {
						LOGGER.info(" { Process id {" + processId.get() +" } found with path { " + process.getFilePath() + " }}");
						boolean status= statusCollector.collectByProcessId(processId.get());
						processStatus = status ;
						
					}
					
					long categoryId = commonApplicationService.getCategoryLookupByCode(process.getCategory()).get().getId();
					long typeId = commonApplicationService.getTypeLookupByCode(process.getType()).get().getId();
					long measurementId = commonApplicationService.getMeasurementByCode(process.getMeasurement()).get().getId();
					
					Status statusMetrics = new Status(getHost(),typeId, categoryId, new Date(), processStatus);
					statusMetrics.setInfo(Category.categoryByCode(process.getCategory()).name());
					statusMetrics.setMeasurement(measurementId);
					statusList.add(statusMetrics);
				});
			
				applicationService.saveStatusInBatch(statusList);
			
		};
	}
	
	public void collectStatusForUpstreamServer() {

		List<Status> statusList = new ArrayList<Status>();
		String upstreamServerDirectory= statusProperties.getUpstreamServer().getDirectoryPath();
		
		if(null == upstreamServerDirectory){
			LOGGER.error("Please provide location path/directory for upstream server.");
		}else {		
			try {
				File directory = new File(upstreamServerDirectory);
				File[]	files = directory.listFiles((File dir, String name) -> name.endsWith(statusProperties.getUpstreamServer().getExtension()));
				List<String> upstreamServers = new ArrayList<String>();
				Arrays.stream(files).forEach(file -> {
				
					String upstreamServerString = fileService.upstreamServerFromFile(file);
					if(upstreamServerString.length() > 0)
						upstreamServers.addAll(upstreamServerIps(upstreamServerString));
				});
				
				upstreamServers.forEach(ip -> {
					boolean status= statusCollector.collectUpstreamServerStatus(ip.trim());
					
					long categoryId = commonApplicationService.getCategoryLookupByCode(statusProperties.getUpstreamServer().getCategory()).get().getId();
					long typeId = commonApplicationService.getTypeLookupByCode(statusProperties.getUpstreamServer().getType()).get().getId();
					long measurementId = commonApplicationService.getMeasurementByCode(statusProperties.getUpstreamServer().getMeasurement()).get().getId();
					
					Status statusMetrics = new Status(getHost(),typeId,categoryId, new Date(), status);
					statusMetrics.setInfo(ip);
					statusMetrics.setMeasurement(measurementId);
					statusList.add(statusMetrics);
				});
				
				applicationService.saveStatusInBatch(statusList);
				
			}catch (Exception e) {
				LOGGER.info("Something went wrong while trying to get status for upstream server",e);
			}	
		}
					
	}
	private List<String> upstreamServerIps(String upstreamServers) {
		List<String> upstreamServerIps = new ArrayList<String>();
		upstreamServerIps=RegularExpressionUtils.listOfMatchedRegex(UPSTREAM_SERVER_REGEX, upstreamServers);
		LOGGER.info("The upstream server found : { " + upstreamServers+" }");
		return upstreamServerIps;
	}
	
	public static void main(String[] args) {
		String ss = "upstream test-app { server 127.0.0.1:8080; server localhost:10;}";
		System.out.println(RegularExpressionUtils.listOfMatchedRegex(UPSTREAM_SERVER_REGEX, ss));
		
	}
	
}
