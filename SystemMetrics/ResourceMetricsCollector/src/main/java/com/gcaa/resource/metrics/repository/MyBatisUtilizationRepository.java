package com.gcaa.resource.metrics.repository;

import java.util.List;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.metrics.domain.model.Utilization;
import com.gcaa.resource.metrics.mapper.UtilizationMapper;

@Repository
public class MyBatisUtilizationRepository extends BatchRepositoy implements UtilizationRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(MyBatisUtilizationRepository.class);
	@Autowired
	private UtilizationMapper utilizationMapper;
	
	@Override
	public boolean save(Utilization utilization) {
		return utilizationMapper.save(utilization);
	}
	
	@Override
	public boolean saveBatch(List<Utilization> utilizations) {
		LOGGER.info("This will insert into database with batch size of list sent as parameter");
		try(SqlSession session = getSessionFactory().openSession(ExecutorType.BATCH);) {
			utilizations.forEach(utilization -> {
				utilizationMapper.save(utilization);
			});
			session.commit();
			
		}catch (Exception e) {
			LOGGER.error("There is something wrong happened in batch insert of { MyBatisUtilizationRepository} ",e);
			return false;
		}
		return true;
	}


}
