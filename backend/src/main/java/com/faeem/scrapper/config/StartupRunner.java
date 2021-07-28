package com.faeem.scrapper.config;

import com.faeem.scrapper.services.ScrapService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class StartupRunner implements ApplicationRunner {
	
	private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

	@Autowired private ScrapService scrapService; 

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Application Started !!!");		
		scrapService.scrapEvents("Application Startup Run");
	}
	
}
