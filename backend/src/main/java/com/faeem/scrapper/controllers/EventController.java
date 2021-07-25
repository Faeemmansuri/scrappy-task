package com.faeem.scrapper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faeem.scrapper.dto.CommonResponseDTO;
import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.dto.EventFilterRequest;
import com.faeem.scrapper.services.EventService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping
	public ResponseEntity<CommonResponseDTO<EventDTO>> getAllEvents(
			@RequestParam(name="startAt", required = false, defaultValue = "0") Integer startAt,
			@RequestParam(name = "maxResults", required = false, defaultValue = "20") Integer maxResults,
			@RequestBody(required = false) EventFilterRequest request
			) {
		System.out.println(startAt + " " + maxResults + " " + request);
		return ResponseEntity.ok(eventService.getEvents(startAt, maxResults, request));
	}

}
