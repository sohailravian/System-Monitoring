package com.gcaa.service.metrics.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.metrics.domain.model.Host;
import com.gcaa.service.metrics.mapper.HostMapper;

@Repository
public class MyBatisHostRepository implements HostRepository {

	@Autowired
	private HostMapper hostMapper;
	
	@Override
	public Optional<Host> getHostByName(String hostName){
		Host host = hostMapper.getHostByName(hostName);
		if(null == host)
			return Optional.empty();
		return Optional.of(host);
	}


}
