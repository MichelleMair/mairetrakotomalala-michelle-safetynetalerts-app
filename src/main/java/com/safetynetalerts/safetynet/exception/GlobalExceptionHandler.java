package com.safetynetalerts.safetynet.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		logger.error("Unhandled exception occurred" , ex);
		
		ErrorDetails errorDetails = new ErrorDetails("An error occurred: ", ex.getMessage());
		
		return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
