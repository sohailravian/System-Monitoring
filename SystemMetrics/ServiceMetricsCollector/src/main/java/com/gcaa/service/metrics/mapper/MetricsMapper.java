package com.gcaa.service.metrics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.gcaa.metrics.domain.model.Metrics;

@Mapper
public interface MetricsMapper {
	boolean save(Metrics metrics);
	boolean saveBatch(List<Metrics> metrics);
}
