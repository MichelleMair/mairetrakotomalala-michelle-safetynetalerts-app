package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
	
	private static final Logger logger= LogManager.getLogger(MedicalRecordServiceImpl.class);
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	/**
	 * GET all Medical Records in JSON file
	 * @return
	 */
	@Override
	public List<MedicalRecordDTO> getAllMedicalRecords() {
		logger.debug("Fetching all medical records from repository. ");
		
		List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllMedicalRecords();
		logger.debug("Converting medicalRecords to DTOs.");
		return medicalRecords.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	/**
	 * ADD (POST) medical record 
	 * @param medicalRecord
	 */
	@Override
	public void addMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		if (medicalrecordDTO.getFirstName() == null || medicalrecordDTO.getLastName() == null) {
			throw new IllegalArgumentException("First name and last name cannot be null");
		}
		
		MedicalRecord medicalRecord = convertToEntity(medicalrecordDTO);
		
		logger.debug("Adding medical record to repository. ", medicalRecord);
		
		List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllMedicalRecords();
		medicalRecords.add(medicalRecord);
		medicalRecordRepository.saveAllMedicalRecords(medicalRecords);
		
		logger.debug("Medical record added successfully: {} ", medicalRecord);
	}
	
	
	/**
	 * UPDATE medical record
	 * @param medicalrecord
	 */
	@Override
	public void updatePerson(MedicalRecordDTO medicalrecordDTO) {
		
		if (medicalrecordDTO.getFirstName() == null || medicalrecordDTO.getLastName() == null) {
			throw new IllegalArgumentException("First name and last name cannot be null");
		}
		List<MedicalRecord> medicalrecords = medicalRecordRepository.getAllMedicalRecords();
		
		logger.debug("Updating medical record in repository: {}", medicalrecordDTO);
		
		boolean recordExists = medicalrecords.stream()
				.anyMatch(m ->m.getFirstName().equals(medicalrecordDTO.getFirstName())
				&& m.getLastName().equals(medicalrecordDTO.getLastName()));
		
		if (!recordExists) {
			throw new IllegalArgumentException("Medical record not found");
		}
		
		medicalrecords.removeIf(m ->m.getFirstName().equals(medicalrecordDTO.getFirstName())
				&& m.getLastName().equals(medicalrecordDTO.getLastName()));
		
		medicalrecords.add(convertToEntity(medicalrecordDTO));
		
		medicalRecordRepository.saveAllMedicalRecords(medicalrecords);
		
		logger.debug("Medical record updated successfully: {} ", medicalrecordDTO);
	}
	
	
	/**
	 * DELETE medical records from JSON file
	 * @param firstName
	 * @param lastName
	 */
	@Override
	public void deleteMedicalRecord(String firstName, String lastName) {
		List<MedicalRecord> medicalrecords = medicalRecordRepository.getAllMedicalRecords();
	
		logger.debug("Deleting medical records from repository with firstName: {} and lastName: {}", firstName, lastName);
	
		boolean recordExists = medicalrecords.removeIf(m ->m.getFirstName().equals(firstName) && m.getLastName().equals(lastName));
		
		if (recordExists) {
			medicalRecordRepository.saveAllMedicalRecords(medicalrecords);
			logger.debug("Medical record deleted successfully with firstName: {} and lastName: {}", firstName, lastName);
			
		} else {
			logger.warn("Medical Record not found with firstname: {} and lastName: {}", firstName, lastName);
			
		}
	}
	
	private MedicalRecordDTO convertToDTO(MedicalRecord MedicalRecord) {
		MedicalRecordDTO dto = new MedicalRecordDTO();
		
		dto.setFirstName(MedicalRecord.getFirstName());
		dto.setLastName(MedicalRecord.getLastName());
		dto.setBirthdate(MedicalRecord.getBirthdate());
		dto.setMedications(MedicalRecord.getMedications());
		dto.setAllergies(MedicalRecord.getAllergies());
		
		return dto;
	}
	
	private MedicalRecord convertToEntity(MedicalRecordDTO dto) {
		MedicalRecord MedicalRecord = new MedicalRecord();
		
		MedicalRecord.setFirstName(dto.getFirstName());
		MedicalRecord.setLastName(dto.getLastName());
		MedicalRecord.setBirthdate(dto.getBirthdate());
		MedicalRecord.setMedications(dto.getMedications());
		MedicalRecord.setAllergies(dto.getAllergies());
		
		return MedicalRecord;
	}
}
