package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.service.CommunityEmailService;

@RestController
public class CommunityEmailController {

	@Autowired
	private CommunityEmailService communityEmailService;
	
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> getEmailsByCity (@RequestParam("city") String city) {
		List<String> emails = communityEmailService.getEmailsByCity(city);
		return ResponseEntity.ok(emails);
	}
}
