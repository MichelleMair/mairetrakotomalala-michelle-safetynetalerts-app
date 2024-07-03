package com.safetynetalerts.safetynet.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
	 
	private static final Logger logger = LogManager.getLogger(LoggingInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.debug("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		if (e != null) {
			logger.error("Request resulted in exception: ", e);
		}
		logger.info("Response: {} {} with status {} .",request.getMethod(), request.getRequestURI(), response.getStatus());
	}
}
