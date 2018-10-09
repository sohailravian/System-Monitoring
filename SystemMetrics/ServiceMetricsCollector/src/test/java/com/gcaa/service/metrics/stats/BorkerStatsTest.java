package com.gcaa.service.metrics.stats;

import static org.junit.Assert.*;
import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
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
public class BorkerStatsTest {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	private static Connection conection;
	
	@Before
	public void init() throws JMSException {
		conection = connectionFactory.createConnection();
		conection.start();
	}
	
	@AfterClass
	public static void tearDown() throws JMSException {
		conection.stop();
		conection.close();
	}
	
	
	@Test
	public void testQueueStatsWithName() {
		try {
			Session session;
			session = conection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Queue replyTo = session.createTemporaryQueue();
			MessageConsumer consumer = session.createConsumer(replyTo);
			 
			String queueName = "ActiveMQ.Statistics.Destination." + "metrics.queue";
			Queue testQueue = session.createQueue(queueName);
			
			MessageProducer producer = session.createProducer(null);
			Message msg = session.createMessage();
			msg.setJMSReplyTo(replyTo);
			producer.send(testQueue, msg);
			 
			MapMessage reply = (MapMessage) consumer.receive();
			assertNotNull(reply);
		 
			for (Enumeration<String> e = reply.getMapNames();e.hasMoreElements();) {
				String name = e.nextElement().toString();
				System.out.println(name + "=" + reply.getObject(name));
			}
			System.out.println("============================");
		}catch (JMSException e1) {
		e1.printStackTrace();
		}
 	}
	
//	@Test
	public void testQueueStatsWithBroker() {
		try {
			Session session;
			session = conection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Queue replyTo = session.createTemporaryQueue();
			MessageConsumer consumer = session.createConsumer(replyTo);
			 
			String queueName = "ActiveMQ.Statistics.Broker";
			Queue testQueue = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(testQueue);
			Message msg = session.createMessage();
			msg.setJMSReplyTo(replyTo);
			producer.send(msg);
			 
			MapMessage reply = (MapMessage) consumer.receive();
			assertNotNull(reply);
			 
			for (Enumeration e = reply.getMapNames();e.hasMoreElements();) {
			  String name = e.nextElement().toString();
			  System.out.println(name + "=" + reply.getObject(name));
			}
			
			System.out.println("============================");
		}catch (Exception e) {
			e.printStackTrace();
		}
 	}

}
