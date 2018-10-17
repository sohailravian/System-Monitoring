package com.gcaa.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gcaa.metrics.domain.model.Host;

@Mapper
public interface HostMapper {
	public Host getHostByName(String hostName);
}
