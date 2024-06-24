package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.model.MedicalRecord;

public interface MedicalRecordService {

	List<MedicalRecord> getAllMedicalRecords();
	void addMedicalRecord(MedicalRecord medicalrecord);
	void updatePerson(MedicalRecord medicalrecord);
	void deleteMedicalRecord(String firstName, String lastName);
}
