package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.model.Person;

public interface PersonService {

	List<Person> getAllPersons();
	void addPerson(Person person);
	void updatePerson(Person person);
	void deletePerson(String firstName, String lastName);
}
