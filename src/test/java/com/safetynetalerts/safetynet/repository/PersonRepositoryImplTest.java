package com.safetynetalerts.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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
	
	@AfterEach
	public void tearDown() {
		verifyNoMoreInteractions(personRepository);
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
	
	@Test
	public void testGetAllPersons() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));	
		persons.add(new Person("Jane","Doe","112 Culver St","Culver","97451","123-456-7890","janedoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		List<Person> result = personRepository.getAllPersons();
		
		//ASSERT
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Jane", result.get(1).getFirstName());
		}
	
	@Test
	public void testUpdatePerson() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		Person johnDoe = new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com");
		persons.add(johnDoe);
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.updatePerson(new PersonDTO("John","Doe","834 Binoc Ave", "Culver", "97451", "987-654-3210","john.new@email.com" ));
		
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());
		
		//Verify that the person's details have been updated
		Person updatedPerson = personRepository.getAllPersons().stream()
				.filter(p -> p.getFirstName().equals("John") 
						&& p.getLastName().equals("Doe")).findFirst().get();
		
		assertEquals("834 Binoc Ave", updatedPerson.getAddress());
		assertEquals("987-654-3210", updatedPerson.getPhone());
		assertEquals("john.new@email.com", updatedPerson.getEmail());
		
	}
	
	@Test
	public void testDeletePerson() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		Person johnDoe = new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com");
		persons.add(johnDoe);
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.deletePerson("John", "Doe");
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());
		
		//Verify that the person has been deleted successfully
		Optional<Person> deletedPerson = personRepository.getAllPersons().stream()
				.filter(p -> p.getFirstName().equals("John") 
						&& p.getLastName().equals("Doe")).findFirst();
		assertEquals(Optional.empty(), deletedPerson);
	}
	
	@Test
	public void testAddPersonWithMissingFieldsThrowsException() {
		//ASSERT
		assertThrows(IllegalArgumentException.class, () -> {
			//ACT
			personService.addPerson(new PersonDTO("","Doe","112 Culver St","","97451","123-456-7890","jdoe@email.com"));
		});
	}
	
	
	@Test
	public void testGetAllPersonsEmpty() {
		//ARRANGE
		when(personRepository.getAllPersons()).thenReturn(new ArrayList<>());
		
		//ACT
		List<Person> result = personRepository.getAllPersons();
		
		//ASSERT
		assertEquals(0, result.size());
	}
	
	@Test
	public void testUpdateNonExistingPerson() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.updatePerson(new PersonDTO("NonExisting", "Person", "NoAddress", "NoCity", "00000", "111-222-333-4444", "noEmail@nomail.com"));
		
		//ASSERT
		verify(personRepository, times(1)).getAllPersons();
		verify(personRepository, times(0)).saveAllPersons(anyList());
	}
	
	@Test
	public void testDeleteNonExistingPerson() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			personService.deletePerson("NonExisting", "Person");
		});
		
		//ADDITIONAL ASSERT
		assertEquals("Person not found", exception.getMessage());
		verify(personRepository, times(1)).getAllPersons();
		verify(personRepository, times(0)).saveAllPersons(anyList());
	}
	
}




