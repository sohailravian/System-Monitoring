package com.gcaa.service.metrics.stats;

import static org.junit.Assert.*;
import java.util.Enumeration;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.gcaa.service.metrics.ServiceMetricsCollectionApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceMetricsCollectionApplication.class})
public class QueueStatsTest {

	@Autowired
	private ActiveMQConnectionFactory connectionFactory;
	
	private static ActiveMQConnection connection;
	
	@Before
	public void init() throws JMSException {
		connection = (ActiveMQConnection) connectionFactory.createConnection();
		connection.start();
	}
	
	@AfterClass
	public static void tearDown() throws JMSException {
		connection.stop();
		connection.close();
	}
	
	
	@Test
	public void testQueueStatsWithName() {
		try {
			
			DestinationSource ds = connection.getDestinationSource();

		    Set<ActiveMQQueue> queues = ds.getQueues();

		    for (ActiveMQQueue activeMQQueue : queues) {
		        try {
		            System.out.println(activeMQQueue.getQueueName());
		        } catch (JMSException e) {
		            e.printStackTrace();
		        }
		    }
		    connection.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
 	}

}
