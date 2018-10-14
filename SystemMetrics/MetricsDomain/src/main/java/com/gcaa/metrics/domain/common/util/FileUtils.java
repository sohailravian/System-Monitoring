package com.gcaa.metrics.domain.common.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	
	public static Optional<Integer> getProcessIdByFilePath(String path){
		
		LOGGER.info(" Opening file with path { " + path + " }");
		try (Stream<String> dataStream = Files.lines(Paths.get(path))) {
			Optional<String> data = dataStream.findFirst();
			Integer processId = Integer.parseInt(data.isPresent() ? data.get().trim() : null);
			return Optional.ofNullable(processId);
			
		} catch (Exception e) {
			LOGGER.error(" Error while opening file with path { " + path + " }", e);
		}
		return Optional.empty();
	}
	
	public static void main(String[] args) {
		float t = 99999999999999999999999999999999999999f;
		System.out.println(t);
	}
	
}
