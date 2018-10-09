package com.gcaa.status.metrics.config;

import java.util.ArrayList;
import java.util.List;

public class CollectorProperties {
	 
	private String frequencyCron;
	private List<Process> processIds = new ArrayList<>();
	
	public static class Process {
		
		private String name;
		private String filePath;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
	}

	public List<Process> getProcessIds() {
		return processIds;
	}
	public void setProcessIds(List<Process> processIds) {
		this.processIds = processIds;
	}	
	public String getFrequencyCron() {
		return frequencyCron;
	}
	public void setFrequencyCron(String frequencyCron) {
		this.frequencyCron = frequencyCron;
	}

}
