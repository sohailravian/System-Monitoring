package com.gcaa.common.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gcaa.common.repository.HostRepository;
import com.gcaa.metrics.domain.model.Host;

@Service
@Transactional(rollbackFor = Throwable.class )
public class CommonApplicationService {
	
	private HostRepository hostRepository;
	
	@Autowired
	public CommonApplicationService(HostRepository hostRepository){
		this.hostRepository = hostRepository;
	}
	
	public Optional<Host> getHostByName(String hostname) {
		return hostRepository.getHostByName(hostname);
	}
	
	
}
