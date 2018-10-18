package com.gcaa.common.repository;

import java.util.Optional;

import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Type;

public interface LookupRepository {
	
	public Optional<Category> getCategoryLookupByCode(String categoryCode);
	public Optional<Type> getTypeLookupByCode(String typeCode);
	public Optional<Measurement> getMeasurementLookupByCode(String measurementCode);
	
}
