package com.gcaa.common.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcaa.common.repository.HostRepository;
import com.gcaa.common.repository.LookupRepository;
import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Host;
import com.gcaa.metrics.domain.model.Measurement;
import com.gcaa.metrics.domain.model.Type;

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
	public Optional<Category> getCategoryLookupByCode(String categoryCode) {
		Optional<Category> optional= lookupRepository.getCategoryLookupByCode(categoryCode.toUpperCase());
		if(!optional.isPresent()) {
			LOGGER.error("Configured category {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create lookup category with id 1. Please correct the category in system.");
			Category lookup = new Category(1,"CAT001","Memory");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
	@Cacheable(value = "type")
	public Optional<Type> getTypeLookupByCode(String typeCode) {
		Optional<Type> optional= lookupRepository.getTypeLookupByCode(typeCode);
		if(!optional.isPresent()) {
			LOGGER.error("Configured type {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create lookup type with id 1. Please correct the type in system.");
			Type lookup = new Type(1,"TYP001","System");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
	@Cacheable(value = "measurement")
	public Optional<Measurement> getMeasurementByCode(String measurementCode) {
		Optional<Measurement> optional= lookupRepository.getMeasurementLookupByCode(measurementCode);
		if(!optional.isPresent()) {
			LOGGER.error("Configured measurement {" + optional + " } of process is not found in lookup table.");
			LOGGER.error("Going to create measurement category with id 1. Please correct the measurement in system.");
			Measurement lookup = new Measurement(1,"MSM001","Consumer");
			return Optional.ofNullable(lookup);
		}
		return optional;
	}
	
}
