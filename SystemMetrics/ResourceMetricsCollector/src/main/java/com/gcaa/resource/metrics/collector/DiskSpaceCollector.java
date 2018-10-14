package com.gcaa.resource.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.HardDisk;
import com.gcaa.metrics.domain.model.Resource;
import com.gcaa.resource.metrics.config.DiskSpaceCollectorProperties;
import com.gcaa.resource.metrics.config.DiskSpaceCollectorProperties.MountPoint;

import static com.gcaa.metrics.domain.common.util.NumberUtils.*;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class DiskSpaceCollector implements Collector {

	private static Logger LOGGER = LoggerFactory.getLogger(DiskSpaceCollector.class);

	private DiskSpaceCollectorProperties diskProperties;
	
	@Autowired
	public DiskSpaceCollector(DiskSpaceCollectorProperties diskProperties) {
		this.diskProperties = diskProperties;
	}
	
	public Optional<Resource> collectDiskSpaceByMountPoint(MountPoint mountPoint) {
		HardDisk disk=null;
		LOGGER.info("{ Disk Metrics Collector Started With Mount Point { " + mountPoint.getPath() + " }}");
		try {
			Path path = Paths.get(mountPoint.getPath());
			FileStore fileStore = Files.getFileStore(path);
			if(null == fileStore) {
				LOGGER.info("{ Disk not found with Mount Point { " + mountPoint.getPath() + " }}");
				return Optional.empty();
			}else {
				double total = Double.valueOf(fileStore.getTotalSpace()) / diskProperties.getUnit();
				double used = total - (Double.valueOf(fileStore.getUsableSpace()) / diskProperties.getUnit());
				disk = new HardDisk(doubleFormatter(used), doubleFormatter(total),mountPoint.getName());
			}
			
		}catch (Exception e) {
			LOGGER.error("{ Some problem occurred while collecting metrics with Mount Point { " + mountPoint.getPath() + " }",e);
		}	
	
		LOGGER.info("{ Disk Metrics Collector Finished " + disk + " }");
		return Optional.ofNullable(disk);

	}

	@Override
	public Optional<Resource> collect() {
		LOGGER.warn("Not implemented");
		return null;
	}
	@Override
	public Optional<Resource> collectByProcessId(int processId) {
		LOGGER.warn("Not implemented");
		return null;
	}
	
	
}

	
