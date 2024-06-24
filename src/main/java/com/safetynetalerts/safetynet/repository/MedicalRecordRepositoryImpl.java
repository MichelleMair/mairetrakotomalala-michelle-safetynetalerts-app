package com.safetynetalerts.safetynet.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynetalerts.safetynet.model.MedicalRecord;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

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
			log.error("Failed to load medical record from json file: {}", DATA_FILEPATH, e);
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
			JsonNode rootNode = objectMapper.createObjectNode();
			((ObjectNode) rootNode).putPOJO("medicalrecords", medicalrecords);
			objectMapper.writeValue(new File(DATA_FILEPATH), rootNode);
		} catch (IOException e) {
			log.error("Failed to save medical records to json file: {}", DATA_FILEPATH, e);
		}
	}
}
