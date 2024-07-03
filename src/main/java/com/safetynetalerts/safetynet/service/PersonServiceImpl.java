package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	private static final Logger logger= LogManager.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;
	
	/**
	 * GET all persons in JSON file
	 * @return
	 */
	@Override
	public List<PersonDTO> getAllPersons() {
		logger.debug("Fetching all persons from repository. ");
		
		List<Person> persons = personRepository.getAllPersons();
		
		logger.debug(" Converting persons to DTOs. ");
		
		return persons.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	/**
	 * ADD (POST) person 
	 * @param person
	 */
	@Override
	public void addPerson(PersonDTO personDTO) {
		Person person = convertToEntity(personDTO);
		
		logger.debug("Adding person to repository. ", person);
		
		List<Person> persons = personRepository.getAllPersons();
		persons.add(person);
		personRepository.saveAllPersons(persons);
		
		logger.debug("Person added successfully: {} ",person);
	}
	
	
	/**
	 * UPDATE person
	 * @param person
	 */
	@Override
	public void updatePerson(PersonDTO personDTO) {
		List<Person> persons = personRepository.getAllPersons();
		
		logger.debug("Updating person in repository: {}", personDTO);
		
		persons.removeIf(p ->p.getFirstName().equals(personDTO.getFirstName())
				&& p.getLastName().equals(personDTO.getLastName()));
		
		persons.add(convertToEntity(personDTO));
		
		personRepository.saveAllPersons(persons);
		
		logger.debug("Person updated successfully: {} ", personDTO);
	}
	
	
	/**
	 * DELETE person from JSON file
	 * @param firstName
	 * @param lastName
	 */
	@Override
	public void deletePerson(String firstName, String lastName) {
		List<Person> persons = personRepository.getAllPersons();
		
		logger.debug("Deleting person from repository with firstName: {} and lastName: {}", firstName, lastName);
		
		persons.removeIf(p ->p.getFirstName().equals(firstName)
				&& p.getLastName().equals(lastName));
		personRepository.saveAllPersons(persons);
		logger.debug("Person deleted successfully with firstName: {} and lastName: {}", firstName, lastName);		
		
	}
	
	private PersonDTO convertToDTO(Person person) {
		PersonDTO dto = new PersonDTO();
		
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setAddress(person.getAddress());
		dto.setCity(person.getCity());
		dto.setZip(person.getZip());
		dto.setPhone(person.getPhone());
		dto.setEmail(person.getEmail());
		
		return dto;
	}
	
	private Person convertToEntity(PersonDTO dto) {
		Person person = new Person();
		
		person.setFirstName(dto.getFirstName());
		person.setLastName(dto.getLastName());
		person.setAddress(dto.getAddress());
		person.setCity(dto.getCity());
		person.setZip(dto.getZip());
		person.setPhone(dto.getPhone());
		person.setEmail(dto.getEmail());
		
		return person;
	}
	
}
