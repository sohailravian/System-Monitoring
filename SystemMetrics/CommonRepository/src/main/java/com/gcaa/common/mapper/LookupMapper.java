package com.gcaa.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gcaa.common.model.CategoryLookup;
import com.gcaa.common.model.MeasurementLookup;
import com.gcaa.common.model.TypeLookup;

@Mapper
public interface LookupMapper {
	
	public CategoryLookup getCategoryLookupByCode(String categoryCode);
	public TypeLookup getTypeLookupByCode(String typeCode);
	public MeasurementLookup getMeasurementLookupByCode(String measurementCode);
}
