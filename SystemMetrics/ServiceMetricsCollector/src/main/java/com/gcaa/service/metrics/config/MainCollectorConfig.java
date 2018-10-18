package com.gcaa.service.metrics.config;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = {"com.gcaa.common.*"})
@ImportResource (locations = {"classpath*:mapper/HostMapper.xml"})
@MapperScan(basePackages = {"com.gcaa.common.mapper","com.gcaa.service.metrics.mapper"})
@Configuration
public class MainCollectorConfig {

	@Autowired
	private ActivemqRestApiProperties activemqRestApiProperties;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate template = builder.build();
		template.getMessageConverters().add(mappingJackson2HttpMessageConverter());
		template.getInterceptors().add(basicAuthentication());
		return template;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		 MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		 mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN));
		 mappingJackson2HttpMessageConverter.getObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		 mappingJackson2HttpMessageConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
		 mappingJackson2HttpMessageConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		 return mappingJackson2HttpMessageConverter;
	}
	
	public BasicAuthorizationInterceptor basicAuthentication() {
		return new BasicAuthorizationInterceptor(activemqRestApiProperties.getUsername() , activemqRestApiProperties.getPassword());
	} 
	
	
}
