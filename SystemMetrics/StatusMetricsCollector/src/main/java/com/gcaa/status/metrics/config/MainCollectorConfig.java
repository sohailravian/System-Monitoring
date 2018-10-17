package com.gcaa.status.metrics.config;

import javax.swing.filechooser.FileSystemView;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import oshi.SystemInfo;

@ComponentScan(basePackages = {"com.gcaa.common.*"})
@ImportResource (locations = {"classpath*:mapper/HostMapper.xml"})
@MapperScan(basePackages = {"com.gcaa.common.mapper","com.gcaa.status.metrics.mapper"})
@Configuration
public class MainCollectorConfig {

	@Bean
	public SystemInfo systemInfo() {
		return new SystemInfo();
	}

	@Bean
	public FileSystemView lileSystemView() {
		return FileSystemView.getFileSystemView();
	}

}
