package com.gcaa.status.metrics.repository;

import java.util.List;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gcaa.metrics.domain.model.Status;
import com.gcaa.status.metrics.mapper.StatusMapper;

@Repository
public class MyBatisStatusRepository extends BatchRepositoy implements StatusRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(MyBatisStatusRepository.class);
	
	@Autowired
	private StatusMapper statusMapper;
	
	@Override
	public boolean save(Status status) {
		return statusMapper.save(status);
	}
	
	@Override
	public boolean saveBatch(List<Status> statuses) {
		LOGGER.info("This will insert into database with batch size of list sent as parameter");
		try(SqlSession session = getSessionFactory().openSession(ExecutorType.BATCH);) {
			statuses.forEach(status -> {
				statusMapper.save(status);
			});
			session.commit();
			
		}catch (Exception e) {
			LOGGER.error("There is something wrong happened in batch insert of { MyBatisStatusRepository} ",e);
			return false;
		}
		return true;
	}


}
