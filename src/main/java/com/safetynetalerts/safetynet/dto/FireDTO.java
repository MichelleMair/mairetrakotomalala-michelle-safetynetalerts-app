package com.safetynetalerts.safetynet.dto;

import java.util.List;

import lombok.Data;

@Data
public class FireDTO {

	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private List<String> medications;
	private List<String> allergies;
	private String stationNumber;
	
}
