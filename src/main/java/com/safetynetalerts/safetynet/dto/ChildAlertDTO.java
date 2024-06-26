package com.safetynetalerts.safetynet.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChildAlertDTO {

	private String firstName;
	private String lastName;
	private int age;
	private List<HouseholdMemberDTO> householdMembers;
}
