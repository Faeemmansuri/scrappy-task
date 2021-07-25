package com.faeem.scrapper.controllers.request;

import java.util.List;

import javax.validation.constraints.NotNull;


public class ScrapRequest {
	
	@NotNull(message =  "{validation.not-null.sites}")
	private List<String> sites;

	public List<String> getSites() {
		return sites;
	}

	public void setSites(List<String> sites) {
		this.sites = sites;
	}

}
