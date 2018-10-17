package com.gcaa.resource.metrics.collector.job;

import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.gcaa.common.service.CommonApplicationService;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Host;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.model.Utilization;
import oshi.SystemInfo;

public class CollectorJob {
	
	public static int HUNDRED_PERCENT=100;
	private Host host;
	
	@Value("${host.name}")
	private String hostName;
	
	@Autowired
	private CommonApplicationService commonApplicationService;
	
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
	
	protected double cpuConsumption(OperatingSystemMXBean operatingSystemMXBean,SystemInfo systemInfo) {
		double used = 0 ;
		used = systemInfo.getHardware().getProcessor().getSystemCpuLoad() * HUNDRED_PERCENT; 
		return used;
	}
	
	protected Utilization utilization(Type type, Category category, String infoType, String info ,Resource resource) {
		Utilization utilization = new Utilization(getHost(), type, category, resource,new Date());
		utilization.setInfo(infoType);
		utilization.setResource(info);
		return utilization;
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
