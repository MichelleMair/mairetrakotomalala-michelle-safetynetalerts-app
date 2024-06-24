package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.PersonDTO;

public interface PersonService {

	List<PersonDTO> getAllPersons();
	void addPerson(PersonDTO person);
	void updatePerson(PersonDTO person);
	void deletePerson(String firstName, String lastName);
}
