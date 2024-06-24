package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;

public interface MedicalRecordService {

	List<MedicalRecordDTO> getAllMedicalRecords();
	void addMedicalRecord(MedicalRecordDTO medicalrecordDTO);
	void updatePerson(MedicalRecordDTO medicalrecordDTO);
	void deleteMedicalRecord(String firstName, String lastName);
}
