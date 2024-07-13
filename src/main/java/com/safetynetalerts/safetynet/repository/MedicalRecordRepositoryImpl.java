package com.safetynetalerts.safetynet.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynetalerts.safetynet.model.MedicalRecord;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of the MedicalRecordRepository interface
 * This class manages the persistence of Medical record data using a JSON file
 * 
 * It provides methods to load, retrieve and save medical record data from/to JSON file
 * The data is initially loaded from the file specified by DATA_FILEPATH
 */
@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
	
	private static final Logger logger= LogManager.getLogger(MedicalRecordRepositoryImpl.class);

	// Path for JSON file
	private static final String DATA_FILEPATH = "src/main/resources/data.json";
	private List<MedicalRecord> medicalrecords = new ArrayList<>();
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * Constructor
	 */
	public MedicalRecordRepositoryImpl() {
		loadData();
	}


	private void loadData() {
		try {
			JsonNode rootNode = objectMapper.readTree(new File(DATA_FILEPATH));
			JsonNode medicalRecordsNode = rootNode.path("medicalrecords");
			medicalrecords = objectMapper.readValue(medicalRecordsNode.toString(), new TypeReference<List<MedicalRecord>>() {});
		} catch (IOException e) {
			logger.error("Failed to load medical record from json file: {}", DATA_FILEPATH, e);
		}
	}

	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalrecords;
	}

	@Override
	public void saveAllMedicalRecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
		try {
			
			//Read existing Json file
			JsonNode rootNode = objectMapper.readTree(new File(DATA_FILEPATH));
			
			//Update section medicalRecords
			((ObjectNode) rootNode).putPOJO("medicalrecords", medicalrecords);
			
			//Write all sections in json file
			objectMapper.writeValue(new File(DATA_FILEPATH), rootNode);
			
		} catch (IOException e) {
			logger.error("Failed to save medical records to json file: {}", DATA_FILEPATH, e);
		}
	}


	@Override
	public MedicalRecord getMedicalRecord(String firstname, String lastName) {
		Optional<MedicalRecord> medicalRecord = medicalrecords.stream()
				.filter(rec -> rec.getFirstName().equalsIgnoreCase(firstname) && rec.getLastName().equalsIgnoreCase(lastName))
				.findFirst();
		return medicalRecord.orElse(null);
	}
}
