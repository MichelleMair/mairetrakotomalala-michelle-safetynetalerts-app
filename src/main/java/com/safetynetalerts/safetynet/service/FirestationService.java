package com.safetynetalerts.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.DataStore;
import com.safetynetalerts.safetynet.model.Firestation;

@Service
public class FirestationService {

	@Autowired
	private DataService dataService;

	public List<Firestation> getAllFirestations() {
		DataStore dataStore = dataService.getDataStore();
		return dataStore.getFirestations();
	}
}
