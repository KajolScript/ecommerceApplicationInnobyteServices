package com.internship.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<String> handleMissingHeader(MissingRequestHeaderException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required header is missing: " + ex.getHeaderName());
	}

}
