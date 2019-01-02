package com.bgcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({ "classpath:/constants.properties" })
public class ConstantsProperties {

	//private static String picTempDir;

	private static String picRegex;
	
	private static String dateformat;


	public static String getPicRegex() {
		return picRegex;
	}

	@Value("${constants.picRegex}")
	public void setPicRegex(String picRegex) {
		ConstantsProperties.picRegex = picRegex;
	}

	public static String getDateformat() {
		return dateformat;
	}

	@Value("${constants.dateformat}")
	public static void setDateformat(String dateformat) {
		ConstantsProperties.dateformat = dateformat;
	}
	
	

}
