package com.gcaa.resource.metrics.config;

import java.util.ArrayList;
import java.util.List;

public class CollectorProperties {
	 
	private String frequencyCron;
	private List<Process> processIds = new ArrayList<>();
	private int processCount;
	private int frequencySeconds;
	private String type;
	private String category;
	
		
	public static class Process {
		
		private String filePath;
		//private String type;
		private String category;
		
		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

	/*	public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}*/

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
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
	public int getProcessCount() {
		return processCount;
	}
	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFrequencySeconds() {
		return frequencySeconds;
	}
	public void setFrequencySeconds(int frequencySeconds) {
		this.frequencySeconds = frequencySeconds;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	
}
