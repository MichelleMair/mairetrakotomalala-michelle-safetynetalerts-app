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
import com.safetynetalerts.safetynet.dto.MedicalRecordDTO;
import com.safetynetalerts.safetynet.service.MedicalRecordService;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testGetAllMedicalRecords() throws Exception {
		List<MedicalRecordDTO> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>()));
		
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecords);
		
		mockMvc.perform(get("/medicalRecords"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].firstName").value("John"));
	}
	
	@Test
	public void testAddMedicalRecord() throws Exception {
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>());
		
		mockMvc.perform(post("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateMedicalRecord() throws Exception {
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>());
		
		mockMvc.perform(put("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteMedicalRecord() throws Exception {
		mockMvc.perform(delete("/medicalRecords")
				.param("firstName", "John")
				.param("lastName", "Doe"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testAddMedicalRecordThrowsException() throws Exception {
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>());
		
		doThrow(new RuntimeException("unexpected error")).when(medicalRecordService).addMedicalRecord(medicalRecordDTO);
		
		mockMvc.perform(post("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordDTO)))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testUpdateMedicalRecordThrowsException() throws Exception {
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John","Doe","03/06/1984", new ArrayList<>(), new ArrayList<>());
		
		doThrow(new RuntimeException("unexpected error")).when(medicalRecordService).updatePerson(medicalRecordDTO);
		
		mockMvc.perform(put("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordDTO)))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testDeleteMedicalRecordThrowsException() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		
		doThrow(new RuntimeException("unexpected error")).when(medicalRecordService).deleteMedicalRecord(firstName, lastName);
		
		mockMvc.perform(delete("/medicalRecords")
				.param("firstName", firstName)
				.param("lastName", lastName))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testUpdateMedicalRecordWithMissingFields() throws Exception {
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
		medicalRecordDTO.setFirstName("");
		medicalRecordDTO.setLastName("Doe");
		
		mockMvc.perform(put("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordDTO)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteMedicalRecordWithMissingFields() throws Exception {
		
		mockMvc.perform(delete("/medicalRecords")
				.param("firstName", "")
				.param("lastName", "Doe"))
		.andExpect(status().isBadRequest());
	}
}
