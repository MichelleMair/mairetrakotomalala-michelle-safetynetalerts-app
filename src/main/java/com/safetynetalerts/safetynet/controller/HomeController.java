package com.safetynetalerts.safetynet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class to handle requests to the home page
 * of the SafetyNet alerts application
 */
@Controller
public class HomeController {

	/**
	 * @return the nam of the view to be rendred ("home")
	 */
	@GetMapping("/")
	public String home() {
		return "home";
	}
}
