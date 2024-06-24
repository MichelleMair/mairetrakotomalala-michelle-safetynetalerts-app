package com.safetynetalerts.safetynet.dto;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecordDTO {

	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies; 
}
