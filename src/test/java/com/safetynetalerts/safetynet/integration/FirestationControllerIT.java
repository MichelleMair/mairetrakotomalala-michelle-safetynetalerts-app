package com.safetynetalerts.safetynet.integration;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.safetynet.dto.FirestationCoverageDTO;
import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.dto.FirestationPersonDTO;
import com.safetynetalerts.safetynet.service.FirestationService;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private FirestationService firestationService;
	
	
	/**
	 * Tests the GET endpoint for retrieving all firestations 
	 * 
	 * This test verifies that the "/firestation" endpoint returns a list of firestations
	 * with the correct status code, content type, and JSON structure
	 * 
	 * The test mock the response from the firestation service to ensure the endpoint 
	 * correctly handles the response
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllFirestations() throws Exception {
		List<FirestationDTO> firestations = new ArrayList<>();
		firestations.add(new FirestationDTO("123 Street Ad", "1"));
		
		when(firestationService.getAllFirestations()).thenReturn(firestations);
		
		mockMvc.perform(get("/firestation"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].address").value("123 Street Ad"));
	}
	
	@Test
	public void testAddFirestation() throws Exception {
		FirestationDTO firestationDTO = new FirestationDTO("123 Street Ad", "1");
		
		mockMvc.perform(post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateFirestation() throws Exception {
		FirestationDTO firestationDTO = new FirestationDTO("123 Street Ad", "1");
		
		mockMvc.perform(put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteFirestation() throws Exception {
		mockMvc.perform(delete("/firestation")
				.param("address", "123 Street Ad")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetCoverageByStationNumber() throws Exception {
		FirestationCoverageDTO coverageDTO = new FirestationCoverageDTO();
		coverageDTO.setNumberOfAdults(2);
		coverageDTO.setNumberOfChildren(1);
		
		List<FirestationPersonDTO> persons = new ArrayList<>();
		persons.add(new FirestationPersonDTO("John","Doe","1234 street Ad" ,"123-456-7890"));
		coverageDTO.setPersons(persons);
		
		when(firestationService.getCoverageByStationNumber(1)).thenReturn(coverageDTO);
		
		mockMvc.perform(get("/firestation?stationNumber=1"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.numberOfAdults").value(2))
		.andExpect(jsonPath("$.numberOfChildren").value(1))
		.andExpect(jsonPath("$.persons[0].firstName").value("John"))
		.andExpect(jsonPath("$.persons[0].lastName").value("Doe"));
		
	}
	
	
	@Test
	public void testAddFirestationThrowsException() throws Exception {
		FirestationDTO firestationDTO = new FirestationDTO("123 Street Ad", "1");
		
		doThrow(new RuntimeException("Unexpected error")).when(firestationService).addFirestation(firestationDTO);
		
		mockMvc.perform(post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationDTO)))
		.andExpect(status().isInternalServerError());
	}
		
	@Test
	public void testDeleteFirestationThrowsException() throws Exception {
		String address = "123 Street Ad";
		
		doThrow(new RuntimeException("Unexpected error")).when(firestationService).deleteFirestation(address);
		
		mockMvc.perform(delete("/firestation")
				.param("address", address)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testAddFirestationWithMissingFields() throws Exception {
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress("");
		firestationDTO.setStation("1");
		
		mockMvc.perform(post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationDTO)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdateFirestationWithMissingFields() throws Exception {
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress("");
		firestationDTO.setStation("1");
		
		mockMvc.perform(put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationDTO)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteFirestationWithMissingFields() throws Exception {
		mockMvc.perform(delete("/firestation")
				.param("address", "")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
}
