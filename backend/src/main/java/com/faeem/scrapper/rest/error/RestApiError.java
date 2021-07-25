package com.faeem.scrapper.rest.error;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.faeem.scrapper.util.ScrappyConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RestApiError implements Serializable {

	private static final long serialVersionUID = 8646337877407937600L;

	private final int status;
	private String errorMessage;
	private List<String> errors;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ScrappyConstants.RESPONSE_DATE_TIME_FORMAT)
	private Date timestamp;
	
	public RestApiError(HttpStatus status) {
		if(Objects.isNull(status) || status.equals(HttpStatus.MULTI_STATUS)) {
			throw new IllegalArgumentException("Invalid or Unknown error status code.");
		}
		this.status = status.value();
		this.timestamp = new Date();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}
	
}
