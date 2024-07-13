package com.safetynetalerts.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
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
	
}
