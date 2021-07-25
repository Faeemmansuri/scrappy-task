package com.faeem.scrapper.repository.custom;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.dto.EventFilterRequest;
import com.faeem.scrapper.entity.EventEntity;

@Component
public class CustomEventRepositoryImpl implements CustomEventRepository {

	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public int saveAll(List<EventDTO> events) {
		StringBuilder queryString = new StringBuilder("INSERT IGNORE INTO events ");
		queryString.append(
				"(event_name,event_type,event_start_date,event_end_date,event_location,event_page_url,created_on)");
		queryString.append(" VALUES ");

		int size = events.size();
		for (int i = 0; i < size; i++) {
			queryString.append("(");
			queryString.append(":event_name" + i + ",");
			queryString.append(":event_type" + i + ",");
			queryString.append(":event_start_date" + i + ",");
			queryString.append(":event_end_date" + i + ",");
			queryString.append(":event_location" + i + ",");
			queryString.append(":event_page_url" + i + ",");
			queryString.append("CURRENT_TIMESTAMP");
			queryString.append(")");
			queryString.append(",");
		}

		queryString.setLength(queryString.length() - 1);

		Query query = em.createNativeQuery(queryString.toString());

		for (int i = 0; i < size; i++) {
			EventDTO eventDTO = events.get(i);
			query.setParameter("event_name" + i, eventDTO.getName());
			query.setParameter("event_type" + i, eventDTO.getType());
			query.setParameter("event_start_date" + i, eventDTO.getStartDate());
			query.setParameter("event_end_date" + i, eventDTO.getEndDate());
			query.setParameter("event_location" + i, eventDTO.getLocation());
			query.setParameter("event_page_url" + i, eventDTO.getPageUrl());
		}
		return query.executeUpdate();
	}

	@Override
	public List<EventEntity> findByEventFilterRequest(EventFilterRequest request, Integer startAt, Integer maxResults) {

		StringBuilder queryString = new StringBuilder("SELECT * FROM events ");
		Query query = generateConditionalQuery(request, queryString, false);

		query.setFirstResult(startAt);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}
	
	@Override
	public Integer findCountByEventFilterRequest(EventFilterRequest request, Integer startAt, Integer maxResults) {
		StringBuilder queryString = new StringBuilder("SELECT count(id) as total FROM events ");
		Query query = generateConditionalQuery(request, queryString, true);
		
		return ((BigInteger) query.getResultList().get(0)).intValue();
	}


	private Query generateConditionalQuery(EventFilterRequest request, StringBuilder queryString, boolean isCountQuery) {
		StringBuilder whereCondition = new StringBuilder("WHERE ");
		boolean filterCrieteria = false;

		
		if (StringUtils.isNotBlank(request.getEventName())) {
			whereCondition.append(" event_name LIKE '%").append(request.getEventName()).append("%' ");
			filterCrieteria = true;
		}

		if (Objects.nonNull(request.getStartDate())) {
			if (filterCrieteria) {
				whereCondition.append(" AND ");
			}
			whereCondition.append(" event_start_date >= :event_start_date");
			filterCrieteria = true;
		}

		if (Objects.nonNull(request.getEndDate())) {
			if (filterCrieteria) {
				whereCondition.append(" AND ");
			}
			whereCondition.append(" event_end_date <= :event_end_date");
			filterCrieteria = true;
		}

		if (CollectionUtils.isNotEmpty(request.getLocation())) {
			if (filterCrieteria) {
				whereCondition.append(" AND ");
			}
			whereCondition.append(" event_location IN :event_location");
			filterCrieteria = true;
		}

		if (CollectionUtils.isNotEmpty(request.getType())) {
			if (filterCrieteria) {
				whereCondition.append(" AND ");
			}
			whereCondition.append(" event_type IN :event_type");
			filterCrieteria = true;
		}

		if(filterCrieteria) {
			queryString.append(whereCondition);
		}
		
		Query query = isCountQuery 
				? em.createNativeQuery(queryString.toString())
				: em.createNativeQuery(queryString.toString(), EventEntity.class);

		if (Objects.nonNull(request.getStartDate())) {
			query.setParameter("event_start_date", request.getStartDate());
		}

		if (Objects.nonNull(request.getEndDate())) {
			query.setParameter("event_end_date", request.getEndDate());
		}

		if (CollectionUtils.isNotEmpty(request.getLocation())) {
			query.setParameter("event_location", request.getLocation());
		}

		if (CollectionUtils.isNotEmpty(request.getType())) {
			query.setParameter("event_type", request.getType());
		}
		return query;
	}
}
