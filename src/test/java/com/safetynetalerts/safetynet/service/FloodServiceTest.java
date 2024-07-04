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

import com.safetynetalerts.safetynet.dto.FloodDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class FloodServiceTest {
	
	@Mock
	private FirestationRepository firestationRepository;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@InjectMocks
	private FloodServiceImpl floodService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetFloodInformation() {
		//ARRANGE
		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St","1"));
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person ("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();	
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		when(personRepository.getAllPersons()).thenReturn(persons);
		when(medicalRecordRepository.getMedicalRecord("John","Boyd")).thenReturn(medicalRecords.get(0));
		
		//ACT
		List<FloodDTO> floodDTO = floodService.getFloodInformation(stationNumbers);
		
		//ASSERT
		assertEquals(1, floodDTO.size());
		assertEquals("1509 Culver St", floodDTO.get(0).getAddress());
		assertEquals(1, floodDTO.get(0).getResidents().size());
		assertEquals("John", floodDTO.get(0).getResidents().get(0).getFirstName());
	}
	
	
	

}
