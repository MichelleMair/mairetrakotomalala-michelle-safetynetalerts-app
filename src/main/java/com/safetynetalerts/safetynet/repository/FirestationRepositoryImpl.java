package com.safetynetalerts.safetynet.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynetalerts.safetynet.model.Firestation;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Repository
public class FirestationRepositoryImpl implements FirestationRepository {

	// Path for JSON file
	private static final String DATA_FILEPATH = "src/main/resources/data.json";
	private List<Firestation> firestations = new ArrayList<>();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//Constructor
	public FirestationRepositoryImpl() {
		loadData();
	}

	private void loadData() {
		try {
			JsonNode rootNode = objectMapper.readTree(new File (DATA_FILEPATH));
			JsonNode firestationsNode = rootNode.path("firestations");
			firestations = objectMapper.readValue(firestationsNode.toString(), 
					new TypeReference<List<Firestation>>() {});
		} catch (IOException e) {
			log.error("Failed to load firstations from json file: {}", DATA_FILEPATH, e);
		}
		
	}
	
	@Override
	public List<Firestation> getAllFirestations() {
		return firestations;
	}
	
	@Override
	public void saveAllFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
		try {
			JsonNode rootNode = objectMapper.createObjectNode();
			((ObjectNode) rootNode).putPOJO("firestations", firestations);
			objectMapper.writeValue(new File(DATA_FILEPATH), rootNode);
		} catch (IOException e) {
			log.error("Failed to save firestations data to json file: {}", DATA_FILEPATH, e);
		}
	}
}
