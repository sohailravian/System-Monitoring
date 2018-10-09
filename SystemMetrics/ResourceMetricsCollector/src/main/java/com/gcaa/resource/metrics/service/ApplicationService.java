package com.gcaa.resource.metrics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gcaa.metrics.domain.model.Utilization;
import com.gcaa.resource.metrics.repository.UtilizationRepository;

@Service
@Transactional(rollbackFor = Throwable.class )
public class ApplicationService {
	
	private UtilizationRepository 	utilizationRepository;
	
	@Autowired
	public ApplicationService(UtilizationRepository utilizationRepository){
		this.utilizationRepository = utilizationRepository;
	}
	
	public boolean saveUtilization(Utilization utilization) {
		return utilizationRepository.save(utilization);
	}
	
	public boolean saveUtilizationInBatch(List<Utilization> utilizations) {
		return utilizationRepository.saveBatch(utilizations);
	}
	
}
