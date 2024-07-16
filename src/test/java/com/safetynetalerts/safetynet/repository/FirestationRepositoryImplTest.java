package com.safetynetalerts.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	
	@Test
	public void testGetAllFirestations() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St", "3"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		List<FirestationDTO> result = firestationService.getAllFirestations();
		
		//ASSERT
		assertEquals(1, result.size());
		assertEquals("1509 Culver St", result.get(0).getAddress());
		assertEquals("3", result.get(0).getStation());
	}
	
	@Test
	public void testAddFirestation() {
		//ARRANGE
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress("1234 Newaddress Nw");
		firestationDTO.setStation("5");
		
		List<Firestation> firestations = new ArrayList<>();
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.addFirestation(firestationDTO);
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
	}
	
	@Test
	public void testUpdateFirestation() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St", "3"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		FirestationDTO updatedFirestationDTO = new FirestationDTO("1509 Culver St", "4");
		
		//ACT
		firestationService.updateFirestation(updatedFirestationDTO);
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
		assertEquals("4", firestations.get(0).getStation());
	}
	
	@Test
	public void testDeleteFirestation() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St", "3"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.deleteFirestation("1509 Culver St");
		
		//ASSERT
		verify(firestationRepository, times(1)).saveAllFirestations(anyList());
		assertTrue(firestations.isEmpty());
	}
	
	@Test
	public void testGetAllFirestationsEmpty() {
		//ARRANGE
		when(firestationRepository.getAllFirestations()).thenReturn(new ArrayList<>());
		
		//ACT
		List<FirestationDTO> result = firestationService.getAllFirestations();
		
		//ASSERT
		assertEquals(0, result.size());
	}
	
	@Test
	public void testUpdateNonExistingFirestation() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		FirestationDTO updatedFirestationDTO = new FirestationDTO("NonExisting address", "5");
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {	
			firestationService.updateFirestation(updatedFirestationDTO);
		});
		
		//ADDITIONNAL ASSERT
		assertEquals("Firestation not found", exception.getMessage());
		
		verify(firestationRepository, times(1)).getAllFirestations();
		verify(firestationRepository, times(0)).saveAllFirestations(anyList());
	}
	
	@Test
	public void testDeleteNonExistingFirestation() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		firestationService.deleteFirestation("NonExisting address");
		
		//ASSERT
		verify(firestationRepository, times(1)).getAllFirestations();
		verify(firestationRepository, times(0)).saveAllFirestations(anyList());
	}
	
	@Test
	public void testAddFirestationWithNullValuesThrowsException() {
		//ARRANGE
		FirestationDTO firestationDTO = new FirestationDTO(null, null);
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			firestationService.addFirestation(firestationDTO);
		});
		
		assertEquals("Address and station cannot be null", exception.getMessage());
	}
	
	@Test
	public void testUpdateFirestationWithNullValuesThrowsException() {
		//ARRANGE	
		FirestationDTO updatedFirestationDTO = new FirestationDTO(null, null);
		
		//ACT & ASSERT
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			firestationService.updateFirestation(updatedFirestationDTO);
		});
		
		assertEquals("Address and station cannot be null", exception.getMessage());
	}
	
	@Test
	public void testGetAllFirestationsWithMultipleEntries() {
		//ARRANGE
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(new Firestation("1509 Culver St", "3"));
		firestations.add(new Firestation("29 15th St", "4"));
		
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);
		
		//ACT
		List<FirestationDTO> result = firestationService.getAllFirestations();
		
		//ASSERT
		assertEquals(2, result.size());
		assertEquals("1509 Culver St", result.get(0).getAddress());
		assertEquals("29 15th St", result.get(1).getAddress());
	}
}
