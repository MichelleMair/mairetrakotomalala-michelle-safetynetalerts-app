package com.safetynetalerts.safetynet.dto;

import java.util.List;

import lombok.Data;

@Data
public class FirestationCoverageDTO {

	private List<FirestationPersonDTO> persons;
	private int numberOfAdults;
	private int numberOfChildren;
}
