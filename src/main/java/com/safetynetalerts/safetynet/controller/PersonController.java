package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	 * 
	 * @param personDTO
	 * @return add person (HTTP 200)
	 */
	@PostMapping
	public ResponseEntity<Void> addPerson(@RequestBody PersonDTO personDTO) {
		logger.debug("Adding person: {}", personDTO);
		
		if(
			personDTO.getFirstName() == null || personDTO.getFirstName().isEmpty() ||
			personDTO.getLastName() == null || personDTO.getLastName().isEmpty()
			) {
			logger.error("First name or last name is missing");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			personService.addPerson(personDTO);
			logger.info("Added person successfully. {}", personDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error adding persons: ", personDTO ,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * 
	 * @param personDTO
	 * @return update info about a person (Response HTTP 200)
	 */
	@PutMapping
	public ResponseEntity<Void> updatePerson(@RequestBody PersonDTO personDTO) {
		logger.debug("Updating person: {}", personDTO);
		
		if(
				personDTO.getFirstName() == null || personDTO.getFirstName().isEmpty() ||
				personDTO.getLastName() == null || personDTO.getLastName().isEmpty()
				) {
				logger.error("First name or last name is missing");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		
		try {
			personService.updatePerson(personDTO);
			logger.info("Updated person successfully: {}", personDTO);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error updating persons: ", personDTO ,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @return delete a person (Response HTTP 200)
	 */
	@DeleteMapping
	public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		logger.debug("Deleting person with firstName: {} and lastName: {}" , firstName, lastName);
		
		if(firstName == null || firstName.isEmpty() ||
			lastName == null || lastName.isEmpty()
			) {
				logger.error("First name or last name is missing");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		
		try {
			personService.deletePerson(firstName, lastName);
			logger.info("Deleted person successfully with firstName: {} and lastName: {}" , firstName, lastName);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			logger.error("Error deleting person with firstName: {} and lastName: {}" , firstName, lastName, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.error("Bad request: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		logger.error("Internal server error: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

}
