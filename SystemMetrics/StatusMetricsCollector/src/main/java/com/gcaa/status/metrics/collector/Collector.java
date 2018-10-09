package com.gcaa.status.metrics.collector;

import java.util.Optional;

public interface Collector {
	
	public static int HUNDRED_PERCENT=100;
	public boolean collectByProcessId(Optional<Integer> processId);
	
}
