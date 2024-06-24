package com.safetynetalerts.safetynet.repository;

import java.util.List;

import com.safetynetalerts.safetynet.model.MedicalRecord;

public interface MedicalRecordRepository {

	List<MedicalRecord> getAllMedicalRecords();
	void saveAllMedicalRecords(List<MedicalRecord> medicalrecords);
}
