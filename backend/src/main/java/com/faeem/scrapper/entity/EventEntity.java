package com.faeem.scrapper.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class EventEntity extends BaseEntity {

	@Column(name = "event_name")
	private String name;

	@Column(name = "event_type", nullable = true)
	private String type;

	@Column(name = "event_start_date")
	private Date startDate;

	@Column(name = "event_end_date")
	private Date endDate;

	@Column(name = "event_location", nullable = true)
	private String location;

	@Column(name = "event_page_url")
	private String pageUrl;

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
