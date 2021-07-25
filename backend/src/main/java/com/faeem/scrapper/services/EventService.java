package com.faeem.scrapper.services;

import com.faeem.scrapper.dto.CommonResponseDTO;
import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.dto.EventFilterRequest;

public interface EventService {

	CommonResponseDTO<EventDTO> getEvents(Integer startAt, Integer maxResults, EventFilterRequest filterRequest);

}
