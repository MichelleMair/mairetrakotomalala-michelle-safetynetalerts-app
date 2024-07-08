package com.safetynetalerts.safetynet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for SafetyNetAlerts application
 * 
 * This class serves as the entry point for the spring boot application
 * 
 * It implements CommandLineRunner to execute additional code after the application context is loaded
 */
@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {
	
	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	/**
	 * Los a message indicating that the application has started with log4j2 configuration.
	 * 
	 * @param args command-line arguments
	 * @throws Exception if an error occurs 
	 */
	@Override
	public void run(String... args) throws Exception {
		logger.info("Application started with Log4j2 configuration.");
		
	}

}
