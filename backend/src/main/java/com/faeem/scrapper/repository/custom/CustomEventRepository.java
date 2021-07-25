package com.faeem.scrapper.repository.custom;

import java.util.List;

import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.dto.EventFilterRequest;
import com.faeem.scrapper.entity.EventEntity;

public interface CustomEventRepository {

	int saveAll(List<EventDTO> events);

	List<EventEntity> findByEventFilterRequest(EventFilterRequest request, Integer startAt, Integer maxResults);

	Integer findCountByEventFilterRequest(EventFilterRequest request, Integer startAt, Integer maxResults);
}
