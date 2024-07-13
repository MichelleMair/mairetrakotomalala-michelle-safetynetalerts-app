package com.safetynetalerts.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.service.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryImplTest {

	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonServiceImpl personService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testSaveAllPersonsDoesNotAffectOtherSections() throws IOException {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.addPerson(new PersonDTO("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());
		
		//Check if any other section has not be change by the action
		JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
		JsonNode medicalRecordsNode = rootNode.path("medicalrecords");
		JsonNode firestationsNode = rootNode.path("firestations");
		
		assertNotNull(medicalRecordsNode, "Medical records section should not be null");
		assertNotNull(firestationsNode, "Firestations section should not be null");
	}
}
