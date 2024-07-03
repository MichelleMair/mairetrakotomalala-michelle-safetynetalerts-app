package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.service.PersonService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/person")
public class PersonController {
	
	private static final Logger logger = LogManager.getLogger(PersonController.class);
  
	@Autowired
	private PersonService personService;
	
	/**
	 * Method GET (CRUD)
	 * @return Response HTTP 200 (ok) list of persons if no error
	 */
	@GetMapping
	public ResponseEntity<List<PersonDTO>> getAllPersons() {
		logger.debug("Fetching all persons");
		
		List<PersonDTO> persons;
		
		try {
			persons = personService.getAllPersons();			
			logger.info("Fetched all persons successfully. Number of persons: {}", persons.size());
			return ResponseEntity.ok(persons);
			
		} catch (Exception e) {
			logger.error("Error fetching all persons: ", e);
			return ResponseEntity.status(500).build();
		}

	}
	
	
	/**
	 * Method POST (CRUD)
	 * @param personDTO
	 * @return add person (HTTP 200)
	 */
	@PostMapping
	public ResponseEntity<Void> addPerson(@RequestBody PersonDTO personDTO) {
		logger.debug("Adding person: {}", personDTO);
		
		try {
			personService.addPerson(personDTO);
			logger.info("Added person successfully. {}", personDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error adding persons: ", personDTO ,e);
			return ResponseEntity.status(500).build();
		}
	}
	
	
	/**
	 * Method PUT (CRUD)
	 * @param personDTO
	 * @return update info about a person (Response HTTP 200)
	 */
	@PutMapping
	public ResponseEntity<Void> updatePerson(@RequestBody PersonDTO personDTO) {
		logger.debug("Updating person: {}", personDTO);
		
		try {
			personService.updatePerson(personDTO);
			logger.info("Updated person successfully: {}", personDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error updating persons: ", personDTO ,e);
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * Method DELETE (CRUD)
	 * @param firstName
	 * @param lastName
	 * @return delete a person (Response HTTP 200)
	 */
	@DeleteMapping
	public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		logger.debug("Deleting person with firstName: {} and lastName: {}" , firstName, lastName);
		
		try {
			personService.deletePerson(firstName, lastName);
			logger.info("Deleted person successfully with firstName: {} and lastName: {}" , firstName, lastName);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error deleting person with firstName: {} and lastName: {}" , firstName, lastName, e);
			return ResponseEntity.status(500).build();
		}
	}

}
