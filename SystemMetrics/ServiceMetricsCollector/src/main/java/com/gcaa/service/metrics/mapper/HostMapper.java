package com.gcaa.service.metrics.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gcaa.metrics.domain.model.Host;

@Mapper
public interface HostMapper {
	
	Host getHostByName(String hostName);
	
}
