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

import com.safetynetalerts.safetynet.dto.ChildAlertDTO;
import com.safetynetalerts.safetynet.dto.HouseholdMemberDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class ChildAlertServiceTest {
	
	@InjectMocks
	private ChildAlertServiceImpl childAlertService;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetChildrenByAddress() {
		//ARRANGE
		String address = "1509 Culver St";
		
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
		persons.add(new Person("Tenley","Boyd","1509 Culver St","Culver","97451","841-874-6512","tenz@email.com"));
	
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Tenley","Boyd", "02/18/2012",new ArrayList<>(), new ArrayList<>()));
	
		when(personRepository.getAllPersons()).thenReturn(persons);
		when(medicalRecordRepository.getMedicalRecord("John","Boyd")).thenReturn(medicalRecords.get(0));
		when(medicalRecordRepository.getMedicalRecord("Tenley","Boyd")).thenReturn(medicalRecords.get(1));
	
		//ACT
		List<ChildAlertDTO> children = childAlertService.getChildrenByAddress(address);
		
		//ASSERT
		assertEquals(1, children.size());
		assertEquals("Tenley", children.get(0).getFirstName());
		assertEquals(12, children.get(0).getAge());
		
		List<HouseholdMemberDTO> householdMembers = children.get(0).getHouseholdMembers();
		assertEquals(1, householdMembers.size());
		assertEquals("John", householdMembers.get(0).getFirstName());
	}
	

}
