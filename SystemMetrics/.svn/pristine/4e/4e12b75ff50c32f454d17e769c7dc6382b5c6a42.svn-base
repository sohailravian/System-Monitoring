package com.gcaa.status.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.State;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import java.util.Optional;

@Component
public class StatusCollector implements Collector {

	private SystemInfo systemInfo;
	
	private static Logger LOGGER = LoggerFactory.getLogger(StatusCollector.class);

	@Autowired
	public StatusCollector(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	@Override
	public boolean collectByProcessId(Optional<Integer> processId) {
		
		LOGGER.info("{ Status Metrics Collector Started }");
		boolean status = false;
		
		if(processId.isPresent()) { 
			LOGGER.info("{ Process id {" + processId.get() +" } found } ");
			OSProcess process = systemInfo.getOperatingSystem().getProcess(processId.get());
			if(null == process) {
				LOGGER.info("{ Process id {" + processId.get() +" } is not running }");
				status = false;
			}else if(State.STOPPED.name().equalsIgnoreCase(process.getState().name()) || State.OTHER.name().equalsIgnoreCase(process.getState().name())){
				status = false;
			}else {
				status = true;
			}
		}
		else{
			LOGGER.info("{ Process id {" + processId.get() +" } did not found }");
			status = false;
		}
		
		LOGGER.info("{ Status Metrics Collector Finished. Status : { " + status + "} }");
		return status;

	}
	
}

	
