package com.safetynetalerts.safetynet.integration;

import static org.mockito.ArgumentMatchers.any;
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
import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.service.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PersonService personService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testGetAllPersons() throws Exception {
		List<PersonDTO> personDTO = new ArrayList<>();
		personDTO.add(new PersonDTO("John","Doe","1234 street Ad","City","56789","123-456-7890","jdoe@email.com"));
		
		when(personService.getAllPersons()).thenReturn(personDTO);
		
		mockMvc.perform(get("/person"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].firstName").value("John"));
	}
	
	
	@Test
	public void testAddPerson() throws Exception {
		PersonDTO personDTO = new PersonDTO("John","Doe","1234 street Ad","City","56789","123-456-7890","jdoe@email.com");
		
		mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
		.andExpect(status().isOk());
	}
	
	
	@Test
	public void testUpdatePerson() throws Exception {
		PersonDTO personDTO = new PersonDTO("John","Doe","1234 street Ad","City","56789","123-456-7890","jdoe@email.com");
		
		mockMvc.perform(put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeletePerson() throws Exception {
		mockMvc.perform(delete("/person")
				.param("firstName", "John")
				.param("lastName", "Doe"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	public void testAddPersonThrowsException() throws Exception {
		PersonDTO personDTO = new PersonDTO("John","Doe","1234 street Ad","City","56789","123-456-7890","jdoe@email.com");
		doThrow(new RuntimeException("Unexpected error")).when(personService).addPerson(personDTO);
		
		mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testUpdatePersonThrowsException() throws Exception {
		PersonDTO personDTO = new PersonDTO("John","Doe","1234 street Ad","City","56789","123-456-7890","jdoe@email.com");
	
		doThrow(new RuntimeException("Unexpected error")).when(personService).updatePerson(personDTO);
		

		mockMvc.perform(put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testDeletePersonThrowsException() throws Exception {
		
		doThrow(new RuntimeException("Unexpected error")).when(personService).deletePerson("John", "Doe");
		
		mockMvc.perform(delete("/person")
				.param("firstName", "John")
				.param("lastName", "Doe"))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testUpdatePersonWithMissingFields() throws Exception {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setFirstName("");
		personDTO.setLastName("Doe");
		
		mockMvc.perform(put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeletePersonWithMissingFields() throws Exception {
		
		mockMvc.perform(delete("/person")
				.param("firstName", "")
				.param("lastName", "Doe"))
		.andExpect(status().isBadRequest());
	}

}
