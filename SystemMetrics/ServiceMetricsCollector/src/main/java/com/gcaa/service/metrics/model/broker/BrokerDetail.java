package com.gcaa.service.metrics.model.broker;

import java.util.ArrayList;
import java.util.List;

public class BrokerDetail {
	
	private int totalConsumerCount;
	private int totalEnqueueCount;
	private int totalConnectionsCount;
	private int totalDequeueCount;
	private List<Destination> queues = new ArrayList<Destination>();
	private int memoryUsageByteCount;
	private List<Destination> topics = new ArrayList<Destination>();
	
	BrokerDetail(){};
	public int getQueuesCount() {
		return queues.size(); 
	}
	public int getTopicCount() {
		return topics.size(); 
	}
	public int getTotalConsumerCount() {
		return totalConsumerCount;
	}
	public void setTotalConsumerCount(int totalConsumerCount) {
		this.totalConsumerCount = totalConsumerCount;
	}
	public List<Destination> getQueues() {
		return queues;
	}
	public void setQueues(List<Destination> queues) {
		this.queues = queues;
	}
	public List<Destination> getTopics() {
		return topics;
	}
	public void setTopics(List<Destination> topics) {
		this.topics = topics;
	}
	public int getTotalEnqueueCount() {
		return totalEnqueueCount;
	}
	public void setTotalEnqueueCount(int totalEnqueueCount) {
		this.totalEnqueueCount = totalEnqueueCount;
	}
	public int getTotalConnectionsCount() {
		return totalConnectionsCount;
	}
	public void setTotalConnectionsCount(int totalConnectionsCount) {
		this.totalConnectionsCount = totalConnectionsCount;
	}
	public int getTotalDequeueCount() {
		return totalDequeueCount;
	}
	public void setTotalDequeueCount(int totalDequeueCount) {
		this.totalDequeueCount = totalDequeueCount;
	}
	
	public int getMemoryUsageByteCount() {
		return memoryUsageByteCount;
	}
	public void setMemoryUsageByteCount(int memoryUsageByteCount) {
		this.memoryUsageByteCount = memoryUsageByteCount;
	}
	
	@Override
	public String toString() {
		return "BrokerDetail [totalConsumerCount=" + totalConsumerCount + ", totalEnqueueCount=" + totalEnqueueCount
				+ ", totalConnectionsCount=" + totalConnectionsCount + ", totalDequeueCount=" + totalDequeueCount
				+ ", queues=" + queues + ", memoryUsageByteCount=" + memoryUsageByteCount + ", topics=" + topics + "]";
	}
	
}
