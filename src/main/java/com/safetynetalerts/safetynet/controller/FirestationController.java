package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.service.FirestationService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/firestation")
public class FirestationController {

	@Autowired
	private FirestationService firestationService;
	
	@GetMapping
	public ResponseEntity<List<Firestation>> getAllFirestations() {
		List<Firestation> firestations = firestationService.getAllFirestations();
		return ResponseEntity.ok(firestations);
	}
	
	
	@PostMapping
	public ResponseEntity<Void> addFirestation(@RequestBody Firestation firestation) {
		firestationService.addFirestation(firestation);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> updateFirestation (@RequestBody Firestation firestation) {
		firestationService.updateFirestation(firestation);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteFirestation(@RequestBody String address) {
		firestationService.deleteFirestation(address);
		return ResponseEntity.ok().build();
	}
}
