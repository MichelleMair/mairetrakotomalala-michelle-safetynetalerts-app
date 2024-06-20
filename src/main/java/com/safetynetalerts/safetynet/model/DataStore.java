package com.safetynetalerts.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class DataStore {
	private List<Person> persons;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalRecords;
}
