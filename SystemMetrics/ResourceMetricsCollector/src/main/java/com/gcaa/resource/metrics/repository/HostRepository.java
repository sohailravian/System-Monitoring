package com.gcaa.resource.metrics.repository;

import java.util.Optional;

import com.gcaa.metrics.domain.model.Host;

public interface HostRepository {
	public Optional<Host> getHostByName(String hostName);
}
