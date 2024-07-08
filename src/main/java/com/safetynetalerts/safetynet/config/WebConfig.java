package com.safetynetalerts.safetynet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.safetynetalerts.safetynet.interceptor.LoggingInterceptor;

/**
 * WebConfig Configuration class for setting up web-related configurations. 
 * 
 * This class implements WebMvcConfigurer to customize the configuration of Spring MVC. 
 * It registers the LoggingInterceptor to intercept requests to specified URL patterns
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private LoggingInterceptor loggingInterceptor;
	
	/**
	 * Adds hte LoggingInterceptor to intercept requests to specified URL patterns. 
	 * 
	 * @param registry : the registry to add the interceptor to 
	 */
	@Override
	public void addInterceptors (InterceptorRegistry registry){
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/person/**", "/firestation/**", "/medicalRecords/**");
	}
}
