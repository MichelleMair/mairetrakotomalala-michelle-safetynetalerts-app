package com.safetynetalerts.safetynet.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynetalerts.safetynet.model.Person;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * In charge of reading and writing JSON objects from a file Using Jackson for
 * serialization and deserialization Convert object to json Convert json to
 * object
 */

@Slf4j
@Repository
public class PersonRepositoryImpl implements PersonRepository {

	
	// Path for JSON file
	private static final String DATA_FILEPATH = "src/main/resources/data.json";
	private List<Person> persons = new ArrayList<>();
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * Constructor
	 */
	public PersonRepositoryImpl() {
		loadData();
	}


	private void loadData() {
		try {
			JsonNode rootNode = objectMapper.readTree(new File(DATA_FILEPATH));
			JsonNode personsNode = rootNode.path("persons");
			persons = objectMapper.readValue(personsNode.toString(), new TypeReference<List<Person>>() {});
		} catch (IOException e) {
			log.error("Failed to load data from json file: {}", DATA_FILEPATH, e);
		}
	}

	@Override
	public List<Person> getAllPersons() {
		return persons;
	}

	@Override
	public void saveAllPersons(List<Person> persons) {
		this.persons = persons;
		try {
			JsonNode rootNode = objectMapper.createObjectNode();
			((ObjectNode) rootNode).putPOJO("persons", persons);
			objectMapper.writeValue(new File(DATA_FILEPATH), rootNode);
		} catch (IOException e) {
			log.error("Failed to save data to json file: {}", DATA_FILEPATH, e);
		}
	}
}
