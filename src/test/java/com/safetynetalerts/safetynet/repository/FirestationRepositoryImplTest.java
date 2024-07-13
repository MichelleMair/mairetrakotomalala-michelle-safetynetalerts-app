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
import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.service.FirestationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FirestationRepositoryImplTest {

	@Mock
	private FirestationRepository firestationRepository;

	@InjectMocks
	private FirestationServiceImpl firestationService; 
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testSaveAllFirestationsDoesNotAffectOtherSections() throws IOException {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();	
		firestations.add(new Firestation("1509 Culver St", "3"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.addFirestation(new FirestationDTO("1509 Culver St", "3"));
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
		
		//Check if any other section has not be change by the action
		JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
		JsonNode personsNode = rootNode.path("persons");
		JsonNode medicalRecordsNode = rootNode.path("medicalrecords");
		
		assertNotNull(personsNode, "Persons section should not be null");
		assertNotNull(medicalRecordsNode, "Medical records section should not be null");
	}
	
}
