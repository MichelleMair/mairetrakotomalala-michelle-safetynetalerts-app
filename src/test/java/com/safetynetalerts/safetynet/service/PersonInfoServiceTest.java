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

import com.safetynetalerts.safetynet.dto.PersonInfoDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@InjectMocks
	private PersonInfoServiceImpl personInfoService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetPersonsInfoByLastName() {
		//ARRANGE
		String lastName = "Boyd";
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();	
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		when(medicalRecordRepository.getMedicalRecord("John","Boyd")).thenReturn(medicalRecords.get(0));
		
		//ACT
		List<PersonInfoDTO> personInfoDTO = personInfoService.getPersonsInfoByLastName(lastName);
		
		//ASSERT
		assertEquals(1, personInfoDTO.size());
		assertEquals("John", personInfoDTO.get(0).getFirstName());
		assertEquals("Boyd", personInfoDTO.get(0).getLastName());
	}
}
