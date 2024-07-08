package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.service.PhoneAlertService;

/**
 * Controller class to handle request related to phone Alerts by firestation number.
 */
@RestController
public class PhoneAlertController {

	@Autowired
	private PhoneAlertService phoneAlertService;
	
	/**
	 * HTTP GET requests
	 * @param stationNumber the firestation number to fetch phone numbers for
	 * @return A ResponseEntity containing a list of phone numbers
	 */
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> getPhoneNumbersByFirestation(@RequestParam("firestation") int stationNumber) {
		List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(stationNumber);
		return ResponseEntity.ok(phoneNumbers);
	}
}
