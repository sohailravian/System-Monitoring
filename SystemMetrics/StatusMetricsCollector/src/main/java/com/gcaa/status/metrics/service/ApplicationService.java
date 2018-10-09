package com.gcaa.status.metrics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gcaa.metrics.domain.model.Status;
import com.gcaa.status.metrics.repository.StatusRepository;

@Service
@Transactional(rollbackFor = Throwable.class )
public class ApplicationService {
	
	private StatusRepository statusRepository;
	
	@Autowired
	public ApplicationService(StatusRepository statusRepository){
		this.statusRepository = statusRepository;
	}
	
	public boolean saveStatus(Status status) {
		return statusRepository.save(status);
	}
	
	public boolean saveStatusInBatch(List<Status> statuses) {
		return statusRepository.saveBatch(statuses);
	}
	
}
