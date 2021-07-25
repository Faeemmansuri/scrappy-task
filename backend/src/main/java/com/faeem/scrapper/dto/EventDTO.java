package com.faeem.scrapper.dto;

import java.util.Date;

import com.faeem.scrapper.util.ScrappyConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EventDTO {
	
	private Long id;
	
	private String name;
	
	private String type;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ScrappyConstants.RESPONSE_DATE_TIME_FORMAT)
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ScrappyConstants.RESPONSE_DATE_TIME_FORMAT)
	private Date endDate;

	private String location;
	
	private String pageUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	

}
