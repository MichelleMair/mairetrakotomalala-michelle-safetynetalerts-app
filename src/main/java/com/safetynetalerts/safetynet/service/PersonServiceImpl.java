package com.safetynetalerts.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	/**
	 * GET all persons in JSON file
	 * @return
	 */
	@Override
	public List<Person> getAllPersons() {
		return personRepository.getAllPersons();
	}
	
	/**
	 * ADD (POST) person 
	 * @param person
	 */
	@Override
	public void addPerson(Person person) {
		List<Person> persons = personRepository.getAllPersons();
		persons.add(person);
		personRepository.saveAllPersons(persons);
	}
	
	
	/**
	 * UPDATE person
	 * @param person
	 */
	@Override
	public void updatePerson(Person person) {
		List<Person> persons = personRepository.getAllPersons();
		persons.removeIf(p ->p.getFirstName().equals(person.getFirstName())
				&& p.getLastName().equals(person.getLastName()));
		
		persons.add(person);
		
		personRepository.saveAllPersons(persons);
	}
	
	
	/**
	 * DELETE person from JSON file
	 * @param firstName
	 * @param lastName
	 */
	@Override
	public void deletePerson(String firstName, String lastName) {
		List<Person> persons = personRepository.getAllPersons();
		persons.removeIf(p ->p.getFirstName().equals(firstName)
				&& p.getLastName().equals(lastName));
		personRepository.saveAllPersons(persons);
	}
	
}
