package com.faeem.scrapper.services;

import org.modelmapper.ModelMapper;

public abstract class BaseService {
	
	protected ModelMapper modelMapper = new ModelMapper();
	
	protected <T> T map(Object source, Class<T> destinationClazz) {
		return modelMapper.map(source, destinationClazz);
	}

}
