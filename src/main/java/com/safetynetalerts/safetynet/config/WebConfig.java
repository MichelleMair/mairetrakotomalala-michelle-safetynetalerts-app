package com.safetynetalerts.safetynet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.safetynetalerts.safetynet.interceptor.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private LoggingInterceptor loggingInterceptor;
	
	@Override
	public void addInterceptors (InterceptorRegistry registry){
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/person/**", "/firestation/**", "/medicalRecords/**");
	}
}
