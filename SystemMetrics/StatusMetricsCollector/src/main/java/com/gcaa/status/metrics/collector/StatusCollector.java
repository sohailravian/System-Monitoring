package com.gcaa.status.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.State;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;

@Component
public class StatusCollector implements Collector {

	private SystemInfo systemInfo;
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollector.class);

	@Autowired
	public StatusCollector(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	@Override
	public boolean collectByProcessId(Integer processId) {
		
		LOGGER.info("{ Status Metrics Collector Started }");
		boolean status = false;
		
		OSProcess process = systemInfo.getOperatingSystem().getProcess(processId);
		if(null == process) {
			LOGGER.info("{ Process id {" + processId +" } is not running }");
			status = false;
		}else if(State.STOPPED.name().equalsIgnoreCase(process.getState().name()) || State.OTHER.name().equalsIgnoreCase(process.getState().name())){
			status = false;
		}else {
			status = true;
		}
	
		LOGGER.info("{ Status Metrics Collector Finished. Status : { " + status + "} }");
		return status;

	}
	
}

	
