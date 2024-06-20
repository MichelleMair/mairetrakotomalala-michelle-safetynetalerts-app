package com.safetynetalerts.safetynet.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.safetynet.model.DataStore;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DataProxy {

	private static final String DATA_FILEPATH= "src/main/resources/data.json";
	
	@Autowired
	private DataStore dataStore;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	public DataProxy() {
		loadData();
	}


	private void loadData() {
		try {
			dataStore = objectMapper.readValue(new File(DATA_FILEPATH), DataStore.class);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public DataStore getDataStore() {
		return dataStore;
	}
	
	public void saveDataStore(DataStore dataStore) {
		try {
			objectMapper.writeValue(new File(DATA_FILEPATH), dataStore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
