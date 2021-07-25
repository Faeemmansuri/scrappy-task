package com.faeem.scrapper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class StartupRunner implements ApplicationRunner {
	
	private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Application Started !!!");		
	}
	
}
