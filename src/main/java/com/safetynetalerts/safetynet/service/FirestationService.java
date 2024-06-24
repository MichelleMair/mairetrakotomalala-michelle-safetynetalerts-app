package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.FirestationDTO;

public interface FirestationService {
	
	List<FirestationDTO> getAllFirestations();
	void addFirestation (FirestationDTO firestationDTO);
	void updateFirestation(FirestationDTO firestationDTO);
	void deleteFirestation(String address);
}
