package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.service.MedicalRecordService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/medicalRecords")
public class MedicalRecordController {
	
	private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

	@Autowired
	private MedicalRecordService medicalRecordService;
	
	/**
	 * 
	 * @return if status ok (200), get a list of medical records
	 */
	@GetMapping
	public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
		logger.debug("Fetching all medical records");
		
		List<MedicalRecordDTO> medicalRecords;
		try {
			medicalRecords = medicalRecordService.getAllMedicalRecords();
			logger.info("Fetched all medical records successfully");
			return ResponseEntity.ok(medicalRecords);
			
		} catch (Exception e) {
			logger.error("Error fetching all medical records: ", e);
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * 
	 * @param medicalRecordDTO
	 * @return if status ok, add medical records
	 */
	@PostMapping
	public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.debug("Adding medical record: {}", medicalRecordDTO);
		
		try {
			medicalRecordService.addMedicalRecord(medicalRecordDTO);
			logger.info("Added medical records successfully. {}", medicalRecordDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error adding medical records: ", medicalRecordDTO ,e);
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * 
	 * @param medicalRecordDTO
	 * @return if status ok, updated medical record successfully
	 */
	@PutMapping
	public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.debug("Updating medical record: {}", medicalRecordDTO);
		
		try {
			medicalRecordService.updatePerson(medicalRecordDTO);
			logger.info("Updated medical record successfully: {}", medicalRecordDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error updating medical record: ", medicalRecordDTO ,e);
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 *
	 * @param firstName
	 * @param lastName
	 * @return delete medical records (Http 200)
	 */
	@DeleteMapping
	public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
		logger.debug("Deleting medical record with firstName: {} and lastName: {}" , firstName, lastName);
		
		try {
			medicalRecordService.deleteMedicalRecord(firstName, lastName);
			logger.info("Deleted medical record successfully with firstName: {} and lastName: {}" , firstName, lastName);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error deleting medical record with firstName: {} and lastName: {}" , firstName, lastName, e);
			return ResponseEntity.status(500).build();
		}
	}

}
