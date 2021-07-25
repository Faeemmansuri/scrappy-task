package com.faeem.scrapper.dto;

import java.util.List;

public class CommonResponseDTO<T> {
	
	private List<T> data;

	private Integer startAt;
	
	private Integer maxResults;
	
	private Integer total;
	
	public CommonResponseDTO() {}
	
	public CommonResponseDTO(List<T> data, Integer startAt, Integer maxResults, Integer total) {
		super();
		this.data = data;
		this.startAt = startAt;
		this.maxResults = maxResults;
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getStartAt() {
		return startAt;
	}

	public void setStartAt(Integer startAt) {
		this.startAt = startAt;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
