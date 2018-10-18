package com.gcaa.service.metrics.collector.job;

import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.gcaa.common.service.CommonApplicationService;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Category1;
import com.gcaa.metrics.domain.model.Host;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Measurement1;
import com.gcaa.metrics.domain.model.Metrics;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.model.Type1;
import com.gcaa.service.metrics.config.ActivemqRestApiProperties;
import com.gcaa.service.metrics.model.broker.Broker;
import com.gcaa.service.metrics.model.broker.QueueDetail;
import com.gcaa.service.metrics.service.ApplicationService;

public class CollectorJob {
	
	public static int HUNDRED_PERCENT=100;
	private Host host;
	protected Type type;
	protected Category category;
	
	@Value("${host.name}")
	private String hostName;
	
	@Autowired
	private CommonApplicationService commonApplicationService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ActivemqRestApiProperties restApiProperties;
	
	@PostConstruct
	public void afterIntilization() {
		
		if(null == hostName) {
			throw new IllegalArgumentException("Please configure host name in properties file."); 
		}
		
		Optional<Host> optHost = commonApplicationService.getHostByName(hostName);
		if(!optHost.isPresent()) {
			throw new IllegalArgumentException("Please configure host name in properties file that should match the name configured in database table {HOST_LOOKUP}");
		}
		
		host = optHost.get();
		
	}
	
	protected Metrics metrics(Type type, Category category, Measurement measurement,String infoType , long value) {
		Metrics metrics = new Metrics(getHost(), type, category, measurement, new Date(), value); 
		metrics.setInfo(infoType);
		return metrics;
	}
	
	protected long measureByMeasurement(String measure, QueueDetail queueDetail) {
		long value = 0;
		switch (Measurement1.measurementByCode(measure)) {
			case NO_OF_MESSAGES:
				value = queueDetail.getQueueSize();
				break;
			case NO_OF_CONSUMBER:
				value = queueDetail.getConsumerCount();
				break;	
			case NO_OF_MESSAGES_DEQUEUED:
				value = queueDetail.getDequeueCount();
				break;
			case NO_OF_MESSAGES_ENQUEUED:
				value = queueDetail.getEnqueueCount();
				break;
			case MEMORY_USAGE_BYTE:
				value = queueDetail.getMemoryUsageByteCount();
				break;	
			default:
				value = queueDetail.getQueueSize();
			}
		return value;
	}
	
	protected long measureByMeasurement(String measure, Broker broker) {
		long value = 0;
		switch (Measurement1.measurementByCode(measure)) {
		case NO_OF_QUEUES:
			value = broker.getValue().getQueuesCount();
			break;
		case NO_OF_TOPICS:
			value = broker.getValue().getTopicCount();
			break;	
		case MEMORY_USAGE_BYTE:
			value = broker.getValue().getMemoryUsageInByte();
			break;	
		default:
			value = broker.getValue().getTotalConsumerCount();
		}
		return value;
	}
	protected String readEndpointForBroker(String brokerEndpoint) {
		return restApiProperties.getHost() + restApiProperties.getReadEndpoint() + brokerEndpoint;
	}
	
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public ActivemqRestApiProperties getRestApiProperties() {
		return restApiProperties;
	}

	public void setRestApiProperties(ActivemqRestApiProperties restApiProperties) {
		this.restApiProperties = restApiProperties;
	}
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public CommonApplicationService getCommonApplicationService() {
		return commonApplicationService;
	}

}
