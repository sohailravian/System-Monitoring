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
public class BrokerCollector implements Collector {

	private static Logger LOGGER = LoggerFactory.getLogger(BrokerCollector.class);
	private RestTemplate restTemplate;
	
	@Autowired
	public BrokerCollector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Optional<Broker> collectBroker(String destinationUrl) {
		
		LOGGER.info("{ Broker Metrics Collector Started with URL { "+ destinationUrl +" }");
		try {
			Broker broker = restTemplate.getForObject(destinationUrl, Broker.class);
			if(null != broker) {
				return Optional.ofNullable(broker);
			}
		}catch (Exception e) {
			LOGGER.error(" Something bad happened with broker metrics while tyring to get stats from URL { "+ destinationUrl+" }",e);
		}
		LOGGER.info("{ Broker Metrics Collector Finished with URL { "+ destinationUrl +" }");
		return Optional.empty();

	}

	@Override
	public Optional<Queue> collectQueue(String destinationUrl) {
		LOGGER.warn("Not implemented for this class");
		return Optional.empty();
	}


	

	
}

	
