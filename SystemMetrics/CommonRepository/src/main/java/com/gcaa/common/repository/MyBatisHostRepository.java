package com.gcaa.common.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.common.mapper.HostMapper;
import com.gcaa.metrics.domain.model.Host;

@Repository
public class MyBatisHostRepository implements HostRepository{

	@Autowired
	private HostMapper hostMapper;
	
	@Override
	public Optional<Host> getHostByName(String hostname){
		Host host = hostMapper.getHostByName(hostname);
		if(null == host)
			return Optional.empty();
		return Optional.of(host);
	}

}
