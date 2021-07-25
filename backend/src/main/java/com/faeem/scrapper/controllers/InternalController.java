package com.faeem.scrapper.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faeem.scrapper.controllers.request.ScrapRequest;
import com.faeem.scrapper.services.ScrapService;

@RestController
@RequestMapping("/internal")
public class InternalController {
	
	@Autowired private ScrapService scrapService; 

	@PostMapping("/scrap")
	public ResponseEntity<Void> scrap(@Valid  @RequestBody ScrapRequest request) {
		scrapService.scrapEvents("test");
		
		
		return ResponseEntity.ok().build(); 
	}
	
}
