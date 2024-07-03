package com.safetynetalerts.safetynet.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;

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
		List<Person> persons = Arrays.asList(
				new Person("John","Doe","112 Culver St","Culver","97451","123-456-7890","jdoe@email.com"),
				new Person("Jane","Doe","112 Culver St","Culver","97451","123-456-7890","janedoe@email.com")
		);
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		List<PersonDTO> personDTO= personService.getAllPersons();
		
		//ASSERT
		assertEquals(2, personDTO.size());
		assertEquals("John", personDTO.get(0).getFirstName());
		assertEquals("Jane", personDTO.get(1).getFirstName());
	}
	
}
