package com.gcaa.status.metrics.config;

import javax.swing.filechooser.FileSystemView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;

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
