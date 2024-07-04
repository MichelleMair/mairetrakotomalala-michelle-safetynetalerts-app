package com.safetynetalerts.safetynet.integration;

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
import com.safetynetalerts.safetynet.dto.FirestationDTO;
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
				.content("123 Street Ad")
				.contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk());
	}
	
}
