package com.safetynetalerts.safetynet.dto;

import lombok.Data;

@Data
public class PersonDTO {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;
}
