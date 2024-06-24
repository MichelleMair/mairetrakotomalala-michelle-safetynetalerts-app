package com.safetynetalerts.safetynet.repository;

import java.util.List;

import com.safetynetalerts.safetynet.model.Person;

public interface PersonRepository {

	List<Person> getAllPersons();
	void saveAllPersons(List<Person> persons);
}
