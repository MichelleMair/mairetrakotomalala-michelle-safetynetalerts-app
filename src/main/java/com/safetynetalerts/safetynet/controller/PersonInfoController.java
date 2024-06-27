package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.PersonInfoDTO;
import com.safetynetalerts.safetynet.service.PersonInfoService;

@RestController
@RequestMapping("/personInfolastName")
public class PersonInfoController {

	@Autowired
	private PersonInfoService personInfoService;
	
	
	@GetMapping(params = "lastName")
	public ResponseEntity<List<PersonInfoDTO>> getPersonsInfoByLastName(@RequestParam("lastName") String lastName) {
		List<PersonInfoDTO> personsInfo= personInfoService.getPersonsInfoByLastName(lastName);
		return ResponseEntity.ok(personsInfo);
	}
}
