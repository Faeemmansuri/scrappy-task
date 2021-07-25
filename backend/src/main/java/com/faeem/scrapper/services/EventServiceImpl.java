package com.faeem.scrapper.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faeem.scrapper.dto.CommonResponseDTO;
import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.dto.EventFilterRequest;
import com.faeem.scrapper.entity.EventEntity;
import com.faeem.scrapper.repository.EventRepository;

@Service
public class EventServiceImpl extends BaseService implements EventService {
	
	private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Autowired EventRepository eventRepository;

	@Override
	public CommonResponseDTO<EventDTO> getEvents(Integer startAt, Integer maxResults, EventFilterRequest filterRequest) {
		List<EventEntity> results = eventRepository.findByEventFilterRequest(filterRequest, startAt, maxResults);
		Integer	total = eventRepository.findCountByEventFilterRequest(filterRequest, startAt, maxResults);
		
		List<EventDTO> data = results.stream().map(event -> map(event, EventDTO.class)).collect(Collectors.toList());
		
		return new CommonResponseDTO<EventDTO>(data, startAt, maxResults, total);
	}
	
	

}
