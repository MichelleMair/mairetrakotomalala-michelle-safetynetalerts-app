package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.service.PhoneAlertService;

@RestController
public class PhoneAlertController {

	@Autowired
	private PhoneAlertService phoneAlertService;
	
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> getPhoneNumbersByFirestation(@RequestParam("firestation") int stationNumber) {
		List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(stationNumber);
		return ResponseEntity.ok(phoneNumbers);
	}
}
