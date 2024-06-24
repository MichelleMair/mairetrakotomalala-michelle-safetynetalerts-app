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
	

	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@GetMapping
	public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
		List<MedicalRecordDTO> medicalRecords = medicalRecordService.getAllMedicalRecords();
		return ResponseEntity.ok(medicalRecords);
	}
	
	@PostMapping
	public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		medicalRecordService.addMedicalRecord(medicalRecordDTO);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		medicalRecordService.updatePerson(medicalRecordDTO);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
		medicalRecordService.deleteMedicalRecord(firstName, lastName);
		return ResponseEntity.ok().build();
	}

}
