package com.gcaa.status.metrics.collector.job;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gcaa.metrics.domain.common.util.FileUtils;
import com.gcaa.metrics.domain.common.util.RegularExpressionUtils;
import com.gcaa.metrics.domain.model.CPU;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.metrics.domain.model.Status;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.status.metrics.collector.StatusCollector;
import com.gcaa.status.metrics.config.StatusCollectorProperties;
import com.gcaa.status.metrics.service.ApplicationService;

@Component
public class StatusCollectorJob extends CollectorJob{
	
	private ApplicationService applicationService;
	private StatusCollectorProperties statusProperties;
	private StatusCollector statusCollector;
	private CacheFileService fileService;
	
	private final static String UPSTREAM_SERVER_IP_REGEX = "([http| https ://]*\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})[:(\\d{1,5})]*"; 
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollectorJob.class);
	
	@Autowired
	public StatusCollectorJob(ApplicationService applicationService, StatusCollectorProperties statusCollectorProperties, StatusCollector statusCollector, CacheFileService fileService) {
		this.applicationService = applicationService;
		this.statusProperties	= statusCollectorProperties; 
		this.statusCollector	= statusCollector;
		this.fileService		= fileService;
	}
	
	@Scheduled(cron = "${status.frequency-cron}")
	public void collectScheduledMemoryUtilization() {
		LOGGER.info("{ Status collection job started }");
		//collectStatusForProcesses();
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
					Status statusMetrics = new Status(getHost(),Type.SERVICE, Category.categoryByCode(process.getCategory()), new Date(), processStatus);
					statusMetrics.setInfo(Category.categoryByCode(process.getCategory()).name());
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
					ip.trim();
					boolean status= statusCollector.collectUpstreamServerStatus(ip);
					Status statusMetrics = new Status(getHost(),Type.SERVICE, Category.categoryByCode(statusProperties.getUpstreamServer().getCategory()), new Date(), status);
					statusMetrics.setInfo(ip);
					statusMetrics.setMeasurement(Measurement.UPSTREAM_SERVER);
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
		upstreamServerIps=RegularExpressionUtils.listOfMatchedRegex(UPSTREAM_SERVER_IP_REGEX, upstreamServers);
		LOGGER.info("The upstream server found : { " + upstreamServers+" }");
		return upstreamServerIps;
	}
	
}
