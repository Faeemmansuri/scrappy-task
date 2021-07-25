package com.faeem.scrapper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeZoneUtil {

	private static final Logger log = LoggerFactory.getLogger(TimeZoneUtil.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public String converDateToResponseFormat(Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}
	
	
}
