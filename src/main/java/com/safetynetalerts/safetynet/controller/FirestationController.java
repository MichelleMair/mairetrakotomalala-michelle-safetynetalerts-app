package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.FirestationCoverageDTO;
import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.service.FirestationService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	private static final Logger logger = LogManager.getLogger(FirestationController.class);

	@Autowired
	private FirestationService firestationService;
	
	/**
	 * 
	 * @return Status ok (200) if no exception (fetching all firestations)
	 */
	@GetMapping
	public ResponseEntity<List<FirestationDTO>> getAllFirestations() {
		logger.debug("Fetching all firestations.");
		
		try {
			List<FirestationDTO> firestations = firestationService.getAllFirestations();
			logger.info("Fetched all firestations successfully. Number of firestations: {} ", firestations.size());
			return ResponseEntity.ok(firestations);
		} catch (Exception e) {
			logger.error("Error fetching all firestations: ", e);
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * @param stationNumber
	 * @return a list of persons covered by the corresponding firestation
	 * if status ok 		
	 */
	@GetMapping(params = "stationNumber")
	public ResponseEntity<FirestationCoverageDTO> getCoverageByStationNumber(@RequestParam("stationNumber") int stationNumber) {
		logger.debug("Getting a list of persons covered by the corresponding firestation.");
		
		try {
			FirestationCoverageDTO coverage = firestationService.getCoverageByStationNumber(stationNumber);
			logger.info("Getting a list of persons covered by the corresponding firestation , successfully.");
			return ResponseEntity.ok(coverage);
		} catch (Exception e) {
			logger.error("Error fetching the list: ", e);
			return ResponseEntity.status(500).build();
		}
	}
	
	
	/**
	 * @param firestationDTO
	 * @return if status ok, adding a firestation
	 */
	@PostMapping
	public ResponseEntity<Void> addFirestation(@RequestBody FirestationDTO firestationDTO) {
		logger.debug("Adding a firestation: {}", firestationDTO);
		
		if(firestationDTO.getAddress() == null || firestationDTO.getAddress().isEmpty() ||
			firestationDTO.getStation() == null || firestationDTO.getStation().isEmpty()
		) {
				logger.error("Address or station is missing");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			firestationService.addFirestation(firestationDTO);
			logger.info("Added firestation successfully: {}", firestationDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error adding a firestation: ", firestationDTO, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * @param firestationDTO
	 * @return if status ok, update a firestation
	 */
	@PutMapping
	public ResponseEntity<Void> updateFirestation (@RequestBody FirestationDTO firestationDTO) {
		logger.debug("Updating a firestation: {}", firestationDTO);
		
		if(firestationDTO.getAddress() == null || firestationDTO.getAddress().isEmpty() ||
		   firestationDTO.getStation() == null || firestationDTO.getStation().isEmpty()
		) {
			logger.error("Address or station is missing");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			firestationService.updateFirestation(firestationDTO);
			logger.info("Updated firestation successfully: {}", firestationDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error updating firestation: ", firestationDTO ,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * @param address
	 * @return if status ok, deleted a firestation successfully
	 */
	@DeleteMapping
	public ResponseEntity<Void> deleteFirestation(@RequestParam(required =false) String address) {
		logger.debug("Deleting a firestation: {}", address);
		
		if(address == null || address.isEmpty()) {
			logger.error("Address is missing");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			firestationService.deleteFirestation(address);
			logger.info("Deleted firestation: {}", address);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Deleted firestation successfully with address: {}", address, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
