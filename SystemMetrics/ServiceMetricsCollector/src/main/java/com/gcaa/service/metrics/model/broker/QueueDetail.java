package com.gcaa.service.metrics.model.broker;

public class QueueDetail {

	private String name;
	private int queueSize;
	private int enqueueCount;
	private int consumerCount;
	private int dequeueCount;
	private long memoryUsageByteCount;

	public long getMemoryUsageByteCount() {
		return memoryUsageByteCount;
	}

	public void setMemoryUsageByteCount(long memoryUsageByteCount) {
		this.memoryUsageByteCount = memoryUsageByteCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnqueueCount() {
		return enqueueCount;
	}

	public void setEnqueueCount(int enqueueCount) {
		this.enqueueCount = enqueueCount;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}

	public int getDequeueCount() {
		return dequeueCount;
	}

	public void setDequeueCount(int dequeueCount) {
		this.dequeueCount = dequeueCount;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	@Override
	public String toString() {
		return "QueueDetail [name=" + name + ", queueSize=" + queueSize + ", enqueueCount=" + enqueueCount
				+ ", consumerCount=" + consumerCount + ", dequeueCount=" + dequeueCount + ", memoryUsageByteCount="
				+ memoryUsageByteCount + "]";
	}
	
}
