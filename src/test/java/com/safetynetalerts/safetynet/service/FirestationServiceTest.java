package com.safetynetalerts.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.safetynetalerts.safetynet.dto.FirestationCoverageDTO;
import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

	@Mock
	private FirestationRepository firestationRepository;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@InjectMocks
	private FirestationServiceImpl firestationService; 
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllFirestations() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("834 Binoc Ave","4"));
		firestations.add(new Firestation("947 E. Rose Dr", "3"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		List<FirestationDTO> firestationDTO = firestationService.getAllFirestations();
		
		//ASSERT
		assertEquals(2, firestationDTO.size());
		assertEquals("834 Binoc Ave", firestationDTO.get(0).getAddress());
		assertEquals("947 E. Rose Dr", firestationDTO.get(1).getAddress());
	}
	
	@Test
	public void testAddFirestation() {
		//ARRANGE
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress("1509 Culver St");
		firestationDTO.setStation("4");
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("29 15th St", "1"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.addFirestation(firestationDTO);
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
	}
	
	@Test
	public void testUpdateFirestation() {
		//ARRANGE
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress("1509 Culver St");
		firestationDTO.setStation("1");
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("29 15th St", "4"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.updateFirestation(firestationDTO);
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
	}
	
	
	@Test
	public void testDeleteFirestation() {
		//ARRANGE
		String address = "748 Townings Dr";
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("748 Townings Dr","3"));
		firestations.add(new Firestation("947 E. Rose Dr", "1"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.deleteFirestation(address);
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
		
	}
	
	@Test
	public void testGetCoverageByStationNumber() {
		//ARRANGE
		int stationNumber = 3;
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
		persons.add(new Person("Jacob","Boyd","1509 Culver St","Culver","97451","841-874-6513","drk@email.com"));
		persons.add(new Person("Tenley","Boyd","1509 Culver St","Culver","97451","841-874-6512","tenz@email.com"));
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St", "3"));
		firestations.add(new Firestation("29 15th St", "2"));
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Jacob","Boyd","03/06/1989",new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Tenley","Boyd", "02/18/2012",new ArrayList<>(), new ArrayList<>()));
		
		when(personRepository.getAllPersons()).thenReturn(persons);
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		when(medicalRecordRepository.getMedicalRecord("John","Boyd")).thenReturn(medicalRecords.get(0));
		when(medicalRecordRepository.getMedicalRecord("Jacob","Boyd")).thenReturn(medicalRecords.get(1));
		when(medicalRecordRepository.getMedicalRecord("Tenley","Boyd")).thenReturn(medicalRecords.get(2));
		
		//ACT
		FirestationCoverageDTO coverage = firestationService.getCoverageByStationNumber(stationNumber);
		
		//ASSERT
		assertEquals(3, coverage.getPersons().size()); //3 address covered by station number 3
		assertEquals(2, coverage.getNumberOfAdults()); //2 adults and 1 child (Tenley Boyd)
		assertEquals(1, coverage.getNumberOfChildren()); //1 Child: Tenley Boyd 02/18/20212
	}
	
}
