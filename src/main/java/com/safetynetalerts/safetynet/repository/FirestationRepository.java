package com.safetynetalerts.safetynet.repository;

import java.util.List;

import com.safetynetalerts.safetynet.model.Firestation;

public interface FirestationRepository {

	List<Firestation> getAllFirestations();
	void saveAllFirestations(List<Firestation> firestations);
}
