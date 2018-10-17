package com.gcaa.common;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.gcaa.common.service.CommonApplicationService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
public class AppTest
{
  /* @Test
   public void testHostByHostname() {
	   String hostname = "SZC-CNS-WKS-004.szcgcaa.local";
	   Optional<Host> host = applicationService.getHostByName(hostname);
	   Assert.assertTrue(host.isPresent());
	   
   }*/
   
   @Test
   public void test() {
	   assertTrue(true);
   }
   
	
}
