package com.safetynetalerts.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.DataStore;
import com.safetynetalerts.safetynet.repository.DataProxy;

@Service 
public class DataService {
	
	@Autowired
	private DataProxy dataProxy;
	
	public DataStore getDataStore() {
		return dataProxy.getDataStore();
	}

	public void saveDataStore(DataStore dataStore) {
		dataProxy.saveDataStore(dataStore);
	}
}
