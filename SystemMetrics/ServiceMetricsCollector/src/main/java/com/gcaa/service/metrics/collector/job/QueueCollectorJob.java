package com.gcaa.service.metrics.collector.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcaa.metrics.domain.model.Category1;
import com.gcaa.metrics.domain.model.Measurement1;
import com.gcaa.metrics.domain.model.Metrics;
import com.gcaa.metrics.domain.model.Type1;
import com.gcaa.service.metrics.collector.QueueCollector;
import com.gcaa.service.metrics.config.QueueCollectorProperties;
import com.gcaa.service.metrics.model.broker.Queue;
import com.gcaa.service.metrics.model.broker.QueueDetail;

@Component
public class QueueCollectorJob extends CollectorJob {
	
	private QueueCollector queueCollector;
	private QueueCollectorProperties collectorProperties;
	private static Logger LOGGER = LoggerFactory.getLogger(QueueCollectorJob.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	public QueueCollectorJob(QueueCollector brokerCollector , QueueCollectorProperties collectorProperties) {
		this.collectorProperties = collectorProperties;
		this.queueCollector = brokerCollector;
	}
	
	@PostConstruct
	public void postInitilization() {
		this.setType(getCommonApplicationService().getTypeLookupByCode(collectorProperties.getType()).get());
		this.setCategory(getCommonApplicationService().getCategoryLookupByCode(collectorProperties.getCategory()).get());
	}
	
	@Scheduled(cron = "${queue.frequency-cron}")
	public void collectScheduledBrokerMetrics() {
		
		LOGGER.info("{ Queue metrics collection job started }");
		collectQueueMetrics();
		LOGGER.info("{ Queue metrics collection job started }");
	}
		
	public void collectQueueMetrics() {
	
		if(!collectorProperties.getMeasure().isEmpty() && collectorProperties.getMeasure().size() >0){
			
		String brokerMetricsUrl = readEndpointForBroker(collectorProperties.getEndpoint());
			Optional<Queue> queue = queueCollector.collectQueue(brokerMetricsUrl);
			if(queue.isPresent()) {
				List<Metrics> metricsList = new ArrayList<Metrics>();
				List<QueueDetail> queueDetails= queueDetails(queue.get());
				for (QueueDetail detail : queueDetails) {
					metricsList.addAll(metrics(detail));
				}
				
				getApplicationService().saveMetricsInBatch(metricsList);
			}
			
		}else {
			LOGGER.info("There is no measure mentioned in properties file with broker name so there is nothing to record...");
		}
	}
	
	private List<Metrics> metrics(QueueDetail queueDetail){
		List<Metrics> metricsList = new ArrayList<Metrics>();
		for (String measure : collectorProperties.getMeasure()) {
			Metrics metrics = metrics(getType(), getCategory() ,getCommonApplicationService().getMeasurementByCode(measure).get(),queueDetail.getName(),measureByMeasurement(measure, queueDetail));
			metricsList.add(metrics);
		}
		return metricsList;
	}
	
	private List<QueueDetail> queueDetails(Queue queue){
		List<QueueDetail> queueDetails = new ArrayList<QueueDetail>();
		queue.getValue().forEach(element ->{
			try {
				QueueDetail detail = objectMapper.treeToValue(element,QueueDetail.class);
				queueDetails.add(detail);
				
			} catch (JsonProcessingException e) {
				LOGGER.error("Something wring happened while converrint queue json object to Queue detail", e);
			}
		});
		
		return queueDetails;
	}
}
