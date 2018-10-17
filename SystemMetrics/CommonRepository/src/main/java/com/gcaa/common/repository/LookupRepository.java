package com.gcaa.common.repository;

import java.util.Optional;
import com.gcaa.common.model.CategoryLookup;
import com.gcaa.common.model.MeasurementLookup;
import com.gcaa.common.model.TypeLookup;

public interface LookupRepository {
	
	public Optional<CategoryLookup> getCategoryLookupByCode(String categoryCode);
	public Optional<TypeLookup> getTypeLookupByCode(String typeCode);
	public Optional<MeasurementLookup> getMeasurementLookupByCode(String measurementCode);
	
}
