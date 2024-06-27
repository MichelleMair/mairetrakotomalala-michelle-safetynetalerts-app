package com.safetynetalerts.safetynet.dto;

import java.util.List;

import lombok.Data;

@Data
public class FloodDTO {

	private String address;
	private List<FireDTO> residents;
}
