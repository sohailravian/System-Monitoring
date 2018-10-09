package com.gcaa.service.metrics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gcaa.metrics.domain.model.Metrics;
import com.gcaa.service.metrics.repository.MetricsRepository;

@Service
@Transactional(rollbackFor = Throwable.class )
public class ApplicationService {
	
	private MetricsRepository metricsRepository ;
	
	@Autowired
	public ApplicationService(MetricsRepository metricsRepository){
		this.metricsRepository = metricsRepository;
	}
	
	public boolean saveMetrics(Metrics metrics) {
		return metricsRepository.save(metrics);
	}
	
	public boolean saveMetricsInBatch(List<Metrics> metrics) {
		return metricsRepository.saveBatch(metrics);
	}
	
}
