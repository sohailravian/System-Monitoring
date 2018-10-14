package com.gcaa.metrics.domain.common.util;

import java.text.DecimalFormat;

public class NumberUtils {

	public static double doubleFormatter(double value) { 
		DecimalFormat df = new DecimalFormat("#.00");
		Double formatedFloat= Double.parseDouble(df.format(value));
		return formatedFloat.doubleValue();
	}
	
	
	public static void main(String[] args) {
		double d=2.4585360229313673E-4;
		System.out.println(String.format("%.4f",d));
	}

}
