package com.safetynetalerts.safetynet.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LoggingInterceptor is an HTTP request interceptor that logs the details of
 * incoming requests and outgoing responses
 * 
 * It implements the HandlerInterceptor interface to 
 * intercept HTTP handled by Spring MVC
 * This interceptor helps in monitoring and debugging by providing detailes logs of HTTP
 * interactions with the application
 * 
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {
	 
	private static final Logger logger = LogManager.getLogger(LoggingInterceptor.class);

	/**
	 * The preHandle method logs the HTTP and URI of incoming requests.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.debug("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
		return true;
	}
	
	/**
	 * The afterCompletion method logs the HTTP method, URI and status of the response
	 * and any exceptions that may have occurred during the request processing
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		if (e != null) {
			logger.error("Request resulted in exception: ", e);
		}
		logger.info("Response: {} {} with status {} .",request.getMethod(), request.getRequestURI(), response.getStatus());
	}
}
