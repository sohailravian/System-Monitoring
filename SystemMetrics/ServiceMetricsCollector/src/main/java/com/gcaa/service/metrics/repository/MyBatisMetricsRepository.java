package com.gcaa.service.metrics.repository;

import java.util.List;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.metrics.domain.model.Metrics;
import com.gcaa.service.metrics.mapper.MetricsMapper;

@Repository
public class MyBatisMetricsRepository extends BatchRepositoy implements MetricsRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(MyBatisMetricsRepository.class);
	@Autowired
	private MetricsMapper metricsMapper;
	
	@Override
	public boolean save(Metrics metrics) {
		return metricsMapper.save(metrics);
	}
	
	@Override
	public boolean saveBatch(List<Metrics> metrics) {
		LOGGER.info("This will insert into database with batch size of list sent as parameter");
		try(SqlSession session = getSessionFactory().openSession(ExecutorType.BATCH);) {
			metrics.forEach(utilization -> {
				metricsMapper.save(utilization);
			});
			session.commit();
			
		}catch (Exception e) {
			LOGGER.error("There is something wrong happened in batch insert of { MyBatisMetricsRepository} ",e);
			return false;
		}
		return true;
	}


}
