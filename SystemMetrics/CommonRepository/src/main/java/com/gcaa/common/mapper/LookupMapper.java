package com.gcaa.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Type;

@Mapper
public interface LookupMapper {
	
	public Category getCategoryLookupByCode(String categoryCode);
	public Type getTypeLookupByCode(String typeCode);
	public Measurement getMeasurementLookupByCode(String measurementCode);

}
