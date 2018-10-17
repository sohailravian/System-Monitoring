package com.gcaa.common.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gcaa.common.model.CategoryLookup;
import com.gcaa.common.model.MeasurementLookup;
import com.gcaa.common.model.TypeLookup;
import com.gcaa.common.repository.HostRepository;
import com.gcaa.common.repository.LookupRepository;
import com.gcaa.metrics.domain.model.Host;

@Service
@Transactional(rollbackFor = Throwable.class )
public class CommonApplicationService {
	
	private HostRepository hostRepository;
	private LookupRepository lookupRepository;
	
	private static Logger LOGGER = LoggerFactory.getLogger(CommonApplicationService.class);
	
	@Autowired
	public CommonApplicationService(HostRepository hostRepository,LookupRepository lookupRepository){
		this.hostRepository = hostRepository;
		this.lookupRepository = lookupRepository;
	}
	
	public Optional<Host> getHostByName(String hostname) {
		return hostRepository.getHostByName(hostname);
	}
	
	@Cacheable(value = "category")
	public Optional<CategoryLookup> getCategoryLookupByCode(String categoryCode) {
		Optional<CategoryLookup> optional= lookupRepository.getCategoryLookupByCode(categoryCode.toUpperCase());
		if(!optional.isPresent()) {
			LOGGER.error("Configured category {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create lookup category with id 1. Please correct the category in system.");
			CategoryLookup lookup = new CategoryLookup(1,"CAT001","Memory");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
	@Cacheable(value = "type")
	public Optional<TypeLookup> getTypeLookupByCode(String typeCode) {
		Optional<TypeLookup> optional= lookupRepository.getTypeLookupByCode(typeCode);
		if(!optional.isPresent()) {
			LOGGER.error("Configured type {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create lookup type with id 1. Please correct the type in system.");
			TypeLookup lookup = new TypeLookup(1,"TYP001","System");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
	@Cacheable(value = "measurement")
	public Optional<MeasurementLookup> getMeasurementByCode(String measurementCode) {
		Optional<MeasurementLookup> optional= lookupRepository.getMeasurementLookupByCode(measurementCode);
		if(!optional.isPresent()) {
			LOGGER.error("Configured measurement {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create measurement category with id 1. Please correct the measurement in system.");
			MeasurementLookup lookup = new MeasurementLookup(1,"MSM001","Consumer");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
}
