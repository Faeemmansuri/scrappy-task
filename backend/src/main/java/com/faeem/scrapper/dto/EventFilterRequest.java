package com.faeem.scrapper.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.faeem.scrapper.util.ScrappyConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EventFilterRequest implements Serializable {

	private static final long serialVersionUID = -4167635504840467431L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ScrappyConstants.RESPONSE_DATE_TIME_FORMAT)
	private Date startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ScrappyConstants.RESPONSE_DATE_TIME_FORMAT)
	private Date endDate;

	private Set<String> location;

	private Set<String> type;

	private String eventName;

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

	public Set<String> getLocation() {
		return location;
	}

	public void setLocation(Set<String> location) {
		this.location = location;
	}

	public Set<String> getType() {
		return type;
	}

	public void setType(Set<String> type) {
		this.type = type;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Override
	public String toString() {
		return "EventFilterRequest [startDate=" + startDate + ", endDate=" + endDate + ", location=" + location
				+ ", type=" + type + ", eventName=" + eventName + "]";
	}
	
	

}
