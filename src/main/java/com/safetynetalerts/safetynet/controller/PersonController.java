package com.safetynetalerts.safetynet.controller;

import java.util.List;

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

import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.service.PersonService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<List<Person>> getAllPersons() {
		List<Person> persons = personService.getAllPersons();
		return ResponseEntity.ok(persons);
	}
	
	@PostMapping
	public ResponseEntity<Void> addPerson(@RequestBody Person person) {
		personService.addPerson(person);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
		personService.updatePerson(person);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		personService.deletePerson(firstName, lastName);
		return ResponseEntity.ok().build();
	}

}
