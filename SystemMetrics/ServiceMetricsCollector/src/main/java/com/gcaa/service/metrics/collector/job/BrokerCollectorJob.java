package com.gcaa.service.metrics.collector.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Metrics;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.service.metrics.collector.BrokerCollector;
import com.gcaa.service.metrics.config.BrokerCollectorProperties;
import com.gcaa.service.metrics.model.broker.Broker;

@Component
public class BrokerCollectorJob extends CollectorJob {
	
	private BrokerCollector brokerCollector;
	private BrokerCollectorProperties collectorProperties;
	private static Logger LOGGER = LoggerFactory.getLogger(BrokerCollectorJob.class);
	
	@Autowired
	public BrokerCollectorJob(BrokerCollector brokerCollector , BrokerCollectorProperties collectorProperties) {
		this.collectorProperties = collectorProperties;
		this.brokerCollector = brokerCollector;
	}
	
	@Scheduled(cron = "${broker.frequency-cron}")
	public void collectScheduledBrokerMetrics() {
		
		LOGGER.info("{ Broker metrics collection job started }");
		collectBrokerMetrics();
		LOGGER.info("{ Broker metrics collection job started }");
	}
		
	public void collectBrokerMetrics() {
	
		if(!collectorProperties.getMeasure().isEmpty() && collectorProperties.getMeasure().size() >0){
			
			String brokerMetricsUrl = readEndpointForBroker(collectorProperties.getEndpoint());
			Optional<Broker> broker = brokerCollector.collectBroker(brokerMetricsUrl);
			if(broker.isPresent()) {
				List<Metrics> metricsList = metrics(broker.get());
				getApplicationService().saveMetricsInBatch(metricsList);
			}
			
		}else {
			LOGGER.info("There is no measure mentioned in properties file with broker name so there is nothing to record...");
		}
	}
	
	private List<Metrics> metrics(Broker broker){
		List<Metrics> metricsList = new ArrayList<Metrics>();
		for (String measure : collectorProperties.getMeasure()) {
			Metrics metrics = metrics(Type.SERVICE, Category.BROKER ,Measurement.measurementByCode(measure),collectorProperties.getName() ,measureByMeasurement(measure, broker));
			metricsList.add(metrics);
		}
		return metricsList;
	}
	
}
