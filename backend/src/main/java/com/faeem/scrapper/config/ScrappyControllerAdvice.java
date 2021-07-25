package com.faeem.scrapper.config;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.faeem.scrapper.common.I18nMessages;
import com.faeem.scrapper.rest.error.RestApiError;
import com.faeem.scrapper.util.RestUtil;
import com.faeem.scrapper.util.TimeZoneUtil;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScrappyControllerAdvice extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ScrappyControllerAdvice.class);
	
	@Value("{spring.mvc.locale}")
	private String defaultLocale;
	
	@Autowired I18nMessages i18Messages;
	
	@Autowired TimeZoneUtil timeZoneUtil;
	
	@InitBinder
	private void activateDirectFieldAccess(DataBinder dataBinder) {
		dataBinder.initDirectFieldAccess();
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Validation Failed : {} ", ex.getMessage());
		List<String> springValidationErrors = ex.getBindingResult().getAllErrors().stream()
				.map(ObjectError::getDefaultMessage).collect(Collectors.toList());

		RestApiError restError = new RestApiError(HttpStatus.BAD_REQUEST);
		restError.setTimestamp(new Date());
		restError.setErrorMessage(
				i18Messages.get("rest.error.validation.failed", RestUtil.extractLocale(request, defaultLocale)));
		restError.setErrors(springValidationErrors);

		return ResponseEntity.badRequest().body(restError);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("No Handler Found : {} ", ex.getMessage());
		RestApiError restError = new RestApiError(status);
		restError.setTimestamp(new Date());
		restError.setErrorMessage(
				i18Messages.get("rest.error.no-api-mapping-found", RestUtil.extractLocale(request, defaultLocale), ((ServletWebRequest) request).getRequest().getRequestURI()));
		
		return new ResponseEntity<>(restError, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Invalid Request Body : {} ", ex.getMessage());
		RestApiError restError = new RestApiError(status);
		restError.setTimestamp(new Date());
		restError.setErrorMessage(
				i18Messages.get("rest.error.invalid-request-body", RestUtil.extractLocale(request, defaultLocale)));
		return new ResponseEntity<>(restError, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Unsupported HTTP Method : {} ", ex.getMessage());
		RestApiError restError = new RestApiError(status);
		restError.setTimestamp(new Date());
		restError.setErrorMessage(
				i18Messages.get("rest.error.http-method-not-supported", RestUtil.extractLocale(request, defaultLocale), ex.getMethod(), ((ServletWebRequest) request).getRequest().getRequestURI()));
		return new ResponseEntity<>(restError, status);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<RestApiError> handleRuntimeException(RuntimeException ex, WebRequest request) {
		log.error("UnExpected Error Occured : {} ", ex.getMessage());
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		RestApiError restError = new RestApiError(status);
		restError.setTimestamp(new Date());
		restError.setErrorMessage(
				i18Messages.get("rest.error.unexpected-error", RestUtil.extractLocale(request, defaultLocale)));
		return new ResponseEntity<>(restError, status);
	}

}
