package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.model.Firestation;

public interface FirestationService {
	
	List<Firestation> getAllFirestations();
	void addFirestation (Firestation firestation);
	void updateFirestation(Firestation firestation);
	void deleteFirestation(String address);
}
