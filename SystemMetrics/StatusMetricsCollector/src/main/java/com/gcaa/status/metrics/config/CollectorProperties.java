package com.gcaa.status.metrics.config;

import java.util.ArrayList;
import java.util.List;

public class CollectorProperties {
	 
	private String frequencyCron;
	private List<Process> processIds = new ArrayList<>();
	
	public static class Process {
		
		private String category;
		private String filePath;
		
		public String getCategory() {
			return category;
		}
		
		public void setCategory(String category) {
			this.category = category;
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
