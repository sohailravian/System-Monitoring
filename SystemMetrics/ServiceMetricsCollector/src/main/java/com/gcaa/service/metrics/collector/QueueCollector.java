package com.gcaa.service.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.gcaa.service.metrics.model.broker.Broker;
import com.gcaa.service.metrics.model.broker.Queue;

import java.util.Optional;

@Component
public class QueueCollector implements Collector {

	private static Logger LOGGER = LoggerFactory.getLogger(QueueCollector.class);
	private RestTemplate restTemplate;

	@Autowired
	public QueueCollector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Optional<Queue> collectQueue(String destinationUrl) {
		
		LOGGER.info("{ Queue Metrics Collector Started with URL { "+ destinationUrl +" }");
		try {
			Queue queue = restTemplate.getForObject(destinationUrl, Queue.class);
			if(null != queue) {
				return Optional.ofNullable(queue);
			}
		}catch (Exception e) {
			LOGGER.error(" Something bad happened for queue metrics while tyring to get stats from URL { "+ destinationUrl+" }",e);
		}
		LOGGER.info("{ Queue Metrics Collector Finished with URL { "+ destinationUrl +" }");
		return Optional.empty();

	}

	@Override
	public Optional<Broker> collectBroker(String destinationUrl) {
		LOGGER.warn("Not implemented for this class");
		return Optional.empty();
	}


	

	
}

	
