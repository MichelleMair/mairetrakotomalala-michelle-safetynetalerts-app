package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.FloodDTO;
import com.safetynetalerts.safetynet.service.FloodService;

@RestController
@RequestMapping("/flood")
public class FloodController {

	@Autowired
	private FloodService floodService;
	
	@GetMapping("/stations")
	public ResponseEntity<List<FloodDTO>> getFloodInformation(@RequestParam("stations") List<Integer> stationNumbers) {
		List<FloodDTO> floodInformation = floodService.getFloodInformation(stationNumbers);
		return ResponseEntity.ok(floodInformation);
	}
}
