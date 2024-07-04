package com.safetynetalerts.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {
	
	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private CommunityEmailServiceImpl communityEmailService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetEmailsByCity() {
		//ARRANGE
		String city = "Culver";
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John", "Boyd", "1509 Culver St", city, "97451", "841-874-6512", "jaboyd@email.com"));
		persons.add(new Person("Jacob", "Boyd", "1509 Culver St", city, "97451", "841-874-6513", "drk@email.com"));
	
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		List<String> emails = communityEmailService.getEmailsByCity(city);
		
		//ASSERT
		assertEquals(2, emails.size());
		assertEquals("jaboyd@email.com", emails.get(0));
		assertEquals("drk@email.com", emails.get(1));
	}

}
