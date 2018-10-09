package com.gcaa.status.metrics.collector.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Status;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.util.FileUtils;
import com.gcaa.status.metrics.collector.StatusCollector;
import com.gcaa.status.metrics.config.StatusCollectorProperties;
import com.gcaa.status.metrics.service.ApplicationService;

@Component
public class StatusCollectorJob extends CollectorJob {
	
	private ApplicationService applicationService;
	private StatusCollectorProperties statusProperties;
	private StatusCollector statusCollector;
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollectorJob.class);
	
	@Autowired
	public StatusCollectorJob(ApplicationService applicationService, StatusCollectorProperties statusCollectorProperties, StatusCollector statusCollector) {
		
		this.applicationService = applicationService;
		this.statusProperties	= statusCollectorProperties; 
		this.statusCollector	= statusCollector;
		
	}
	
	@Scheduled(cron = "${status.frequency-cron}")
	public void collectScheduledMemoryUtilization() {
		LOGGER.info("{ Status collection job started }");
		collectStatusForProcesses();
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
						boolean status= statusCollector.collectByProcessId(processId);
						processStatus = status ;
						
					}
					Status statusMetrics = new Status(getHost(),Type.SERVICE, Category.categoryByCode(process.getName()), new Date(), processStatus);
					statusMetrics.setInfo(Category.categoryByCode(process.getName()).name());
					statusList.add(statusMetrics);
				});
			
				applicationService.saveStatusInBatch(statusList);
			
		};
	}

}
