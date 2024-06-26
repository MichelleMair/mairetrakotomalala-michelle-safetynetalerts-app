package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.FireDTO;
import com.safetynetalerts.safetynet.service.FireService;

@RestController
@RequestMapping("/fire")
public class FireController {

	@Autowired
	private FireService fireService;
	
	@GetMapping(params = "address")
	public ResponseEntity<List<FireDTO>> getPersonsByAddress(@RequestParam("address") String address){
		List<FireDTO> persons = fireService.getPersonsByAddress(address);
		return ResponseEntity.ok(persons);
	}
}
