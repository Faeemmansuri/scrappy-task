package com.faeem.scrapper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faeem.scrapper.entity.EventEntity;
import com.faeem.scrapper.repository.custom.CustomEventRepository;

public interface EventRepository extends BaseRepository<EventEntity, Long>, CustomEventRepository {

	Page<EventEntity> findAll(Pageable pageable);
	
	Page<EventEntity> findByNameContaining(String name, Pageable pageable);
	
}
