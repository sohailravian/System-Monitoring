package com.gcaa.common.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.common.mapper.LookupMapper;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Type;

@Repository
public class MyBatisLookupRepository implements LookupRepository{

	@Autowired
	private LookupMapper lookupMapper;

	@Override
	public Optional<Category> getCategoryLookupByCode(String categoryCode) {
		Category lookup = lookupMapper.getCategoryLookupByCode(categoryCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	@Override
	public Optional<Type> getTypeLookupByCode(String typeCode) {
		Type lookup = lookupMapper.getTypeLookupByCode(typeCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	@Override
	public Optional<Measurement> getMeasurementLookupByCode(String measurementCode) {
		Measurement lookup = lookupMapper.getMeasurementLookupByCode(measurementCode);
		if(null == lookup)
			return Optional.empty();
		return Optional.of(lookup);
	}

	
	

}
