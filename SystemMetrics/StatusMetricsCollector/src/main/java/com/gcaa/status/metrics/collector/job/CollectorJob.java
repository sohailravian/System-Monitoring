package com.gcaa.status.metrics.collector.job;

import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.gcaa.common.service.CommonApplicationService;
import com.gcaa.metrics.domain.model.Host;

@Component
public class CollectorJob {
	
	protected final static String UPSTREAM_SERVER_REGEX = "\\s*server\\s*(\\S*[\\:{0,1}\\d{1,5}]{0,1});";
	public static int HUNDRED_PERCENT=100;
	private Host host;
	
	@Value("${host.name}")
	private String hostName;
	
/*	@Autowired
	private HostRepository hostRepository;*/
	
	@Autowired
	private CommonApplicationService commonApplicationService;
	
	@PostConstruct
	public void afterIntilization() {
	
		if(null == hostName) 
			throw new IllegalArgumentException("Please configure host name in properties file."); 

		//Optional<Host> optHost = hostRepository.getHostByName(hostName);
		Optional<Host> optHost = commonApplicationService.getHostByName(hostName);
		if(!optHost.isPresent()) {
			throw new IllegalArgumentException("Please configure host name in properties file that should match the name configured in database HOST_LOOKUP table.");
		}
		
		host = optHost.get();
		
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

}
