package com.gcaa.resource.metrics;

import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.gcaa.metrics.domain.model.Category;
import com.gcaa.metrics.domain.model.Host;
import com.gcaa.metrics.domain.model.Type;
import com.gcaa.metrics.domain.model.Utilization;
import com.gcaa.resource.metrics.repository.UtilizationRepository;
import com.gcaa.resource.metrics.repository.MyBatisUtilizationRepository;
import com.gcaa.metrics.domain.model.Memory;
import oshi.SystemInfo;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MyBatisUtilizationRepository.class)
@Rollback(value = false)
public class MyBatisMetricsTest {

	
	@Autowired
	private UtilizationRepository metricsRepository;
	
	private SystemInfo info;
	
	@Before
	public void init() {
		info = new SystemInfo();
	}
	
	@Test
	public void saveMetrics() {
		
		Utilization metrics = new Utilization(new Host(1, "Machine 1"), 
									  Type.SERVICE, 
									  Category.RIE, 
									  new Memory(10,50),
									  new Date() 
									  );
		
		metrics.setInfo("Test Utilization");
		
		boolean result = metricsRepository.save(metrics);
		assertTrue(result);
		
		
	}
	
	
}
