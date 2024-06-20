package com.safetynetalerts.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.service.DataService;

import lombok.Data;

@Data
@RestController
public class DataController {

	@Autowired
	private DataService dataService;
	
	
}
