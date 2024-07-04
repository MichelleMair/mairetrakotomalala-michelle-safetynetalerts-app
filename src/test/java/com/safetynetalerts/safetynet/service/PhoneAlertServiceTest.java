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

import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {
	
	@Mock
	private FirestationRepository firestationRepository;
	
	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PhoneAlertServiceImpl phoneAlertService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetPhoneNumbersByFirestation() {
		//ARRANGE
		int stationNumber = 3;
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St","3"));
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person ("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		when(personRepository.getAllPersons()).thenReturn(persons);
		
		//ACT
		List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(stationNumber);
		
		//ASSERT
		assertEquals(1, phoneNumbers.size());
		assertEquals("841-874-6512", phoneNumbers.get(0));
		
	}
}
