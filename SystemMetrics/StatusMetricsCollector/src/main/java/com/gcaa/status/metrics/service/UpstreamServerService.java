package com.gcaa.status.metrics.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UpstreamServerService {
	
	private final static String UPSTREAM_SERVER 				= "upstream";
	private final static String UPSTREAM_SERVER_END_DELIMETER	= "}";
	
	private static Logger LOGGER = LoggerFactory.getLogger(UpstreamServerService.class);
	
	@Cacheable(value = "upstreamServers",key= "#file.name")
	public String upstreamServerFromFile(File file) {
		StringBuilder upstreamServer = new StringBuilder("");
		try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
			Iterator<String> fileLineItr = stream.iterator();
			boolean upstreamServerFound = false;
			while(fileLineItr.hasNext()) {
				String fileLine = fileLineItr.next();
				
				if(upstreamServerFound && !fileLine.contains(UPSTREAM_SERVER_END_DELIMETER)) {
					upstreamServer.append(fileLine);
				}else if(upstreamServerFound && fileLine.contains(UPSTREAM_SERVER_END_DELIMETER)){
					String closingString = fileLine.substring(0, fileLine.indexOf(UPSTREAM_SERVER_END_DELIMETER));
					upstreamServer.append(closingString);
					upstreamServer.append(UPSTREAM_SERVER_END_DELIMETER);
					upstreamServerFound = false;
				}
				
				if(fileLine.contains(UPSTREAM_SERVER)) {
					upstreamServer.append(fileLine);
					upstreamServerFound = true;
					if(fileLine.contains(UPSTREAM_SERVER_END_DELIMETER))
						upstreamServerFound = false;
				}
			}
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
		LOGGER.info("Upstream server found in file : {"+ upstreamServer.toString()+" }");
		return upstreamServer.toString();
	}
}
