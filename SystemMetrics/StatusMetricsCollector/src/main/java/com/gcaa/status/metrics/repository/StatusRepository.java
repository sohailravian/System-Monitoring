package com.gcaa.status.metrics.repository;

import java.util.List;
import com.gcaa.metrics.domain.model.Status;

public interface StatusRepository {
		
	public boolean save(Status utilization);
	public boolean saveBatch(List<Status> statuses);
	
}
