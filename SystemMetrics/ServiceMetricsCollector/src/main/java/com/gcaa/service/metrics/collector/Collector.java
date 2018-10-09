package com.gcaa.service.metrics.collector;

import java.util.Optional;
import com.gcaa.service.metrics.model.broker.Broker;
import com.gcaa.service.metrics.model.broker.Queue;

public interface Collector {

	public Optional<Broker> collectBroker(String destinationUrl);
	public Optional<Queue> collectQueue(String destinationUrl);
	
}
