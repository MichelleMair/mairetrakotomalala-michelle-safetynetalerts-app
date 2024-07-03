package com.safetynetalerts.safetynet.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonServiceImpl personService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllPersons() {
		//ARRANGE
		List<Person> persons = new ArrayList<>();
		persons.add(new Person ("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));
		persons.add(new Person ("Jane","Doe","112 Culver St","Culver","97451","123-456-7890","janedoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		List<PersonDTO> personDTO= personService.getAllPersons();
		
		//ASSERT
		assertEquals(2, personDTO.size());
		assertEquals("John", personDTO.get(0).getFirstName());
		assertEquals("Jane", personDTO.get(1).getFirstName());
	}
	
	@Test
	public void testAddPerson() {
		//ARRANGE
		PersonDTO personDTO = new PersonDTO();
		
		personDTO.setFirstName("John");
		personDTO.setLastName("Doe");
		personDTO.setAddress("112 Culver St");
		personDTO.setCity("Culver");
		personDTO.setZip("97451");
		personDTO.setPhone("123-456-7890");
		personDTO.setEmail("jdoe@email.com");
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Jane","Doe","112 Culver St","Culver","97451","123-456-7890","janedoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.addPerson(personDTO);
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());;
	}
	
	@Test
	public void testUpdatePerson() {
		//ARRANGE
		PersonDTO personDTO = new PersonDTO();
		
		personDTO.setFirstName("John");
		personDTO.setLastName("Doe");
		personDTO.setAddress("834 Binoc Ave");
		personDTO.setCity("NewCulver");
		personDTO.setZip("15479");
		personDTO.setPhone("098-765-4321");
		personDTO.setEmail("johnd@email.com");
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person ("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.updatePerson(personDTO);
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());
	}
	
	@Test
	public void testDeletePerson() {
		//ARRANGE
		String firstName= "John";
		String lastName = "Doe";
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"));
		persons.add(new Person("Jane","Doe","112 Culver St","Culver","97451","123-456-7890","janedoe@email.com"));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		personService.deletePerson(firstName, firstName);
		
		//ASSERT
		verify(personRepository, times(1)).saveAllPersons(anyList());
	}
	
}
