package com.gcaa.metrics.domain.model;

import oshi.software.os.OSProcess;

public class GcaaOSProcess {
	
	private Resource resource;
	private OSProcess osProcess;
		
	public GcaaOSProcess(Resource recource,OSProcess osProcess){
		this.setResource(recource);
		this.osProcess = osProcess;
	}
	
	public OSProcess getOsProcess() {
		return osProcess;
	}

	public void setOsProcess(OSProcess osProcess) {
		this.osProcess = osProcess;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
