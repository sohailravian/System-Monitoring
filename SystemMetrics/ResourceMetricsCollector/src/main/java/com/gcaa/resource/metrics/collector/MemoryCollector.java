package com.gcaa.resource.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gcaa.metrics.domain.model.Memory;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.resource.metrics.config.MemoryCollectorProperties;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import static com.gcaa.metrics.domain.util.NumberUtils.*;

import java.util.Optional;

@Component
public class MemoryCollector implements Collector {

	private SystemInfo systemInfo;
	private static Logger LOGGER = LoggerFactory.getLogger(MemoryCollector.class);
	private MemoryCollectorProperties memoryProperties;

	@Autowired
	public MemoryCollector(SystemInfo systemInfo, MemoryCollectorProperties collectorProperties) {
		this.systemInfo = systemInfo;
		this.memoryProperties = collectorProperties;
	}

	@Override
	public Optional<Resource> collect() {

		LOGGER.info("{ Memory Metrics Collector Started }");
		double total = Double.valueOf(systemInfo.getHardware().getMemory().getTotal()) / memoryProperties.getUnit();
		double used  = total - ( Double.valueOf(systemInfo.getHardware().getMemory().getAvailable()) / memoryProperties.getUnit());
		
		Memory memory = new Memory(doubleFormatter(used), doubleFormatter(total));
		LOGGER.info("{ Memory Metrics Collector Finished { " + memory + " }}");
		return Optional.ofNullable(memory);

	}

	@Override
	public Optional<Resource> collectByProcessId(int processId) {
		
		Optional<Resource> memory = Optional.empty();
		LOGGER.info("{ Memory Metrics Collector Started With Process Id { " + processId + " }}");
		OSProcess process = systemInfo.getOperatingSystem().getProcess(processId);
		
		if(null == process) {
			LOGGER.info("{ Memory metrics not found with process { "+ processId +" }}");
		}else {
			double used	= (double)(process.getResidentSetSize()) / (double)memoryProperties.getUnit();
			double total = Double.valueOf(systemInfo.getHardware().getMemory().getTotal()) / memoryProperties.getUnit();;
			memory =  Optional.ofNullable(new Memory(doubleFormatter(used), doubleFormatter(total)));
		}
	
		LOGGER.info("{ Memory Metrics Collector Finished. " + memory + " }");
		return memory;

	}

	
}

	
