package com.safetynetalerts.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	/**
	 * GET all Medical Records in JSON file
	 * @return
	 */
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordRepository.getAllMedicalRecords();
	}
	
	/**
	 * ADD (POST) medical record 
	 * @param medicalRecord
	 */
	@Override
	public void addMedicalRecord(MedicalRecord medicalrecord) {
		List<MedicalRecord> medicalrecords = medicalRecordRepository.getAllMedicalRecords();
		medicalrecords.add(medicalrecord);
		medicalRecordRepository.saveAllMedicalRecords(medicalrecords);
	}
	
	
	/**
	 * UPDATE medical record
	 * @param medicalrecord
	 */
	@Override
	public void updatePerson(MedicalRecord medicalrecord) {
		List<MedicalRecord> medicalrecords = medicalRecordRepository.getAllMedicalRecords();
		medicalrecords.removeIf(m ->m.getFirstName().equals(medicalrecord.getFirstName())
				&& m.getLastName().equals(medicalrecord.getLastName()));
		
		medicalrecords.add(medicalrecord);
		
		medicalRecordRepository.saveAllMedicalRecords(medicalrecords);
	}
	
	
	/**
	 * DELETE medical records from JSON file
	 * @param firstName
	 * @param lastName
	 */
	@Override
	public void deleteMedicalRecord(String firstName, String lastName) {
	List<MedicalRecord> medicalrecords = medicalRecordRepository.getAllMedicalRecords();
		medicalrecords.removeIf(m ->m.getFirstName().equals(firstName)
				&& m.getLastName().equals(lastName));
		medicalRecordRepository.saveAllMedicalRecords(medicalrecords);
	}
}
