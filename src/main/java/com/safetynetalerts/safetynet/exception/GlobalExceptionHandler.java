package com.safetynetalerts.safetynet.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * GlobalExceptionHandler is a centralized exception handling component
 * It captures and handles all exceptions that occur within the application,
 * providing a consistent response structure and logging the errors
 * for debugging purposes
 * 
 * It uses the @ControllerAdvice annotation to apply global exception handling
 * to all controllers, ensuring that exceptions are caught and handled appropriately
 * without exposing internal server details 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * @param ex
	 * @param request
	 * @return a structured error response with an INTERNAL_SERVER_ERROR (500) status
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		logger.error("Unhandled exception occurred" , ex);
		
		ErrorDetails errorDetails = new ErrorDetails("An error occurred: ", ex.getMessage());
		
		return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
