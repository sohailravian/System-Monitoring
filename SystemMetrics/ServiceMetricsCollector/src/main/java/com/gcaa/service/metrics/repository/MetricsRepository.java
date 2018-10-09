package com.gcaa.service.metrics.repository;

import java.util.List;
import com.gcaa.metrics.domain.model.Metrics;

public interface MetricsRepository {
		
	public boolean save(Metrics metrics);
	public boolean saveBatch(List<Metrics> metrics);
	
}
