package com.gcaa.common.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.common.mapper.LookupMapper;
import com.gcaa.common.model.CategoryLookup;
import com.gcaa.common.model.MeasurementLookup;
import com.gcaa.common.model.TypeLookup;

@Repository
public class MyBatisLookupRepository implements LookupRepository{

	@Autowired
	private LookupMapper lookupMapper;

	@Override
	public Optional<CategoryLookup> getCategoryLookupByCode(String categoryCode) {
		CategoryLookup lookup = lookupMapper.getCategoryLookupByCode(categoryCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	@Override
	public Optional<TypeLookup> getTypeLookupByCode(String typeCode) {
		TypeLookup lookup = lookupMapper.getTypeLookupByCode(typeCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	@Override
	public Optional<MeasurementLookup> getMeasurementLookupByCode(String measurementCode) {
		MeasurementLookup lookup = lookupMapper.getMeasurementLookupByCode(measurementCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	
	

}
