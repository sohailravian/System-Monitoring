package com.gcaa.resource.metrics.repository;

import java.util.List;

import com.gcaa.metrics.domain.model.Utilization;

public interface UtilizationRepository {
		
	public boolean save(Utilization utilization);
	public boolean saveBatch(List<Utilization> utilizations);
	
}
