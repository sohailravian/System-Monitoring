package com.gcaa.resource.metrics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gcaa.metrics.domain.model.Utilization;

@Mapper
public interface UtilizationMapper {
	
	boolean save(Utilization utilization);
	boolean saveBatch(List<Utilization> utilizations);
}
