package com.safetynetalerts.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.DataStore;
import com.safetynetalerts.safetynet.model.Person;

@Service
public class PersonService {

	@Autowired
	private DataService dataService;
	
	/**
	 * GET all persons in JSON file
	 * @return
	 */
	public List<Person> getAllPersons() {
		DataStore dataStore = dataService.getDataStore();
		return dataStore.getPersons();
	}
	
	/**
	 * ADD person 
	 * @param person
	 */
	public void addPerson(Person person) {
		DataStore dataStore = dataService.getDataStore();
		dataStore.getPersons().add(person);
		dataService.saveDataStore(dataStore);
	}
	
	
	/**
	 * UPDATE person
	 * @param person
	 */
	public void updatePerson(Person person) {
		DataStore dataStore = dataService.getDataStore();
		dataStore.getPersons().removeIf(p ->p.getFirstName().equals(person.getFirstName())
				&& p.getLastName().equals(person.getLastName()));
		
		dataStore.getPersons().add(person);
		
		dataService.saveDataStore(dataStore);
	}
	
	
	/**
	 * DELETE person from JSON file
	 * @param firstName
	 * @param lastName
	 */
	public void deletePerson(String firstName, String lastName) {
		DataStore dataStore = dataService.getDataStore();
		dataStore.getPersons().removeIf(p ->p.getFirstName().equals(firstName)
				&& p.getLastName().equals(lastName));
		dataService.saveDataStore(dataStore);
	}
	
}
