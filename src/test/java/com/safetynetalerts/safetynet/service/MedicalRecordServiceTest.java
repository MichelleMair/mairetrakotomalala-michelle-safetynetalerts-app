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

import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@InjectMocks
	private MedicalRecordServiceImpl medicalRecordService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllMedicalRecords() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Jane","Doe","03/06/1989",new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		List<MedicalRecordDTO> medicalRecordDTO = medicalRecordService.getAllMedicalRecords();
		
		//ASSERT
		assertEquals(2, medicalRecordDTO.size());
		assertEquals("John", medicalRecordDTO.get(0).getFirstName());
		assertEquals("Jane", medicalRecordDTO.get(1).getFirstName());
	}
	
	@Test
	public void testAddMedicalRecord() {
		//ARRANGE
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
		
		medicalRecordDTO.setFirstName("John");
		medicalRecordDTO.setLastName("Doe");
		medicalRecordDTO.setBirthdate("03/06/1984");
		medicalRecordDTO.setMedications(new ArrayList<>());
		medicalRecordDTO.setAllergies(new ArrayList<>());
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("Jane", "Doe", "06/03/1989", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.addMedicalRecord(medicalRecordDTO);
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
	}
	
	@Test
	public void testUpdateMedicalRecord() {
		//ARRANGE
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
		
		medicalRecordDTO.setFirstName("John");
		medicalRecordDTO.setLastName("Doe");
		medicalRecordDTO.setBirthdate("03/06/1984");
		medicalRecordDTO.setMedications(new ArrayList<>());
		medicalRecordDTO.setAllergies(new ArrayList<>());
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John", "Doe", "03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.updatePerson(medicalRecordDTO);
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
	}
	
	@Test
	public void testDeleteMedicalRecord() {
		//ARRANGE
		String firstName = "John";
		String lastName = "Doe";
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();	
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Jane","Doe","03/06/1989",new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.deleteMedicalRecord(firstName, lastName);
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
	}
	
}
