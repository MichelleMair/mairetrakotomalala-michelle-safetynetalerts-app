package com.safetynetalerts.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.service.MedicalRecordServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryImplTest {
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@InjectMocks
	private MedicalRecordServiceImpl medicalRecordService;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testSaveAllMedicalRecordsDoesNotAffectOtherSections() throws IOException {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.addMedicalRecord(new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
		
		//Check if any other section has not be change by the action
		JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
		JsonNode personsNode = rootNode.path("persons");
		JsonNode firestationsNode = rootNode.path("firestations");
		
		assertNotNull(personsNode, "Persons section should not be null");
		assertNotNull(firestationsNode, "Firestations section should not be null");
		
	}
	
	@Test
	public void testGetAllMedicalRecords() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		List<MedicalRecordDTO> result = medicalRecordService.getAllMedicalRecords();
		
		//ASSERT
		assertEquals(1, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Doe", result.get(0).getLastName());
	}
	
	@Test
	public void testAddMedicalRecord() {
		//ARRANGE
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("Jane", "Doe", "01/01/1990", new ArrayList<>(), new ArrayList<>());
		
		//ACT
		medicalRecordService.addMedicalRecord(medicalRecordDTO);
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
	}
	
	@Test
	public void testUpdateMedicalRecord() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		MedicalRecordDTO updatedMedicalRecordDTO = new MedicalRecordDTO("John","Doe","03/06/1985", new ArrayList<>(), new ArrayList<>()); 
		
		//ACT
		medicalRecordService.updatePerson(updatedMedicalRecordDTO);
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
		assertEquals("03/06/1985", medicalRecords.get(0).getBirthdate());
	}
	
	@Test
	public void testDeleteMedicalRecord() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.deleteMedicalRecord("John", "Doe");
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).saveAllMedicalRecords(anyList());
		assertTrue(medicalRecords.isEmpty());
	}
	
	@Test
	public void testGetMedicalRecord() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));

		when(medicalRecordRepository.getMedicalRecord("John", "Doe")).thenReturn(medicalRecords.get(0));

		
		//ACT
		MedicalRecord result = medicalRecordRepository.getMedicalRecord("John", "Doe");
		
		//ASSERT
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
	}
	
	@Test
	public void testGetNonExistentMedicalRecord() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		
		//ACT
		MedicalRecord result = medicalRecordRepository.getMedicalRecord("Non","Existent");
		
		//ASSERT
		assertNull(result);
	}
	
	@Test
	public void testGetAllMedicalRecordsEmpty() {
		//ARRANGE
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(new ArrayList<>());
		
		//ACT
		List<MedicalRecordDTO> result = medicalRecordService.getAllMedicalRecords();
		
		//ASSERT
		assertEquals(0, result.size());
	}
	
	
	@Test
	public void testDeleteNonExistingMedicalRecord() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		medicalRecordService.deleteMedicalRecord("NonExisting", "Person");
		
		//ASSERT
		verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
		verify(medicalRecordRepository, times(0)).saveAllMedicalRecords(anyList());
	}
	
	@Test
	public void testUpdateNonExistingMedicalRecord() {
	    //ARRANGE
	    List<MedicalRecord> medicalRecords = new ArrayList<>();
	    when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);

	    MedicalRecordDTO nonExistentMedicalRecordDTO = new MedicalRecordDTO("NonExistent", "Person", "01/01/2000", new ArrayList<>(), new ArrayList<>());

	    //ACT & ASSERT
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        medicalRecordService.updatePerson(nonExistentMedicalRecordDTO);
	    });

	    // VERIFY THE EXCEPTION MESSAGE
	    assertEquals("Medical record not found", exception.getMessage());

	    // VERIFY INTERACTIONS WITH REPOSITORY
	    verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
	    verify(medicalRecordRepository, times(0)).saveAllMedicalRecords(anyList());
	}
	
	@Test
	public void testAddMedicalRecordWithNullValuesThrowsException() {
		//ARRANGE
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(null, null, null, null, null);
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			medicalRecordService.addMedicalRecord(medicalRecordDTO);
		});
		
		//ADDITIONAL ASSERT
		assertEquals("First name and last name cannot be null" , exception.getMessage());
	}
	
	@Test
	public void testUpdateMedicalRecordWithNullValuesThrowsException () {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		lenient().when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		MedicalRecordDTO updatedMedicalRecordDTO = new MedicalRecordDTO(null, null, null, null, null);
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			medicalRecordService.updatePerson(updatedMedicalRecordDTO);
		});
		
		assertEquals("First name and last name cannot be null" , exception.getMessage());
	}
	
	@Test
	public void testGetAllMedicalRecordsWithMultipleEntries() {
		//ARRANGE
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Jake","Smith","06/03/1990", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		//ACT
		List<MedicalRecordDTO> result = medicalRecordService.getAllMedicalRecords();
		
		//ASSERT
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Jake", result.get(1).getFirstName());
	}
	
}
