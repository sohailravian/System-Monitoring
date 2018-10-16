package com.gcaa.metrics.domain.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegularExpressionUtils {
	
	public static List<String> listOfMatchedRegex(String regex, String value){
	
		List<String> matchedResult = new ArrayList<String>();
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		 while (matcher.find()) {
			 matchedResult.add(matcher.group(1));
		}
		return matchedResult;
	}
	
}
