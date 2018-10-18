package com.gcaa.status.metrics.collector;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.gcaa.metrics.domain.model.CPU;
import com.gcaa.metrics.domain.model.Category1;
import com.gcaa.metrics.domain.model.State;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;

@Component
public class StatusCollector implements Collector {

	private SystemInfo systemInfo;
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollector.class);
	private final String HTTP = "http";
	private final String HTTPS = "https";
	private final int TIME_TO_CONNNECT = 1000; 

	@Autowired
	public StatusCollector(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	@Override
	public boolean collectByProcessId(Integer processId) {
		
		LOGGER.info("{ Status Metrics Collector Started With PID : {"+processId+" }}");
		boolean status = false;
		
		OSProcess process = systemInfo.getOperatingSystem().getProcess(processId);
		if(null == process) {
			LOGGER.info("{ Process id {" + processId +" } is not found }");
			status = false;
		}else if(State.STOPPED.name().equalsIgnoreCase(process.getState().name()) || State.OTHER.name().equalsIgnoreCase(process.getState().name())){
			status = false;
		}else {
			status = true;
		}
		LOGGER.info("{ Status Metrics Collector Finished With PID : { "+processId+ "} . Status : { " + status + "} }");
		return status;
	}
	
	public boolean collectUpstreamServerStatus(String url) {
		
		LOGGER.info("{ Status Metrics Collector For Upstream Server Started With IP : { "+ url +" }");
		boolean status = false;
		if(!url.contains(HTTP) && !url.contains(HTTPS))
			url = HTTP + "://" + url.trim();
		try {
			URL urlToHit = new URL(url);
			HttpURLConnection.setFollowRedirects(false);
		    HttpURLConnection httpURLConnection = (HttpURLConnection) urlToHit.openConnection();
		    httpURLConnection.setConnectTimeout(TIME_TO_CONNNECT);
		    httpURLConnection.setRequestMethod("HEAD");
		    // Some websites don't like programmatic access so pretend to be a browser
		    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
		    int responseCode = httpURLConnection.getResponseCode();
		    if(responseCode != HttpURLConnection.HTTP_NOT_FOUND)
		    	status = true;
		    
		}catch (Exception e) {
			LOGGER.info("Something went wrong while trying to acess IP { "+ url +"}",e);
		}
		
		LOGGER.info("{ Status Metrics Collector For Upstream Server Finished With IP { "+url +"} . Status : { " + status + "} }");
		return status;
	}
	
	
}

	
