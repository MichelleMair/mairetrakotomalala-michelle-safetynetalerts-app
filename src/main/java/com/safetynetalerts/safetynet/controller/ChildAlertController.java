package com.safetynetalerts.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.safetynet.dto.ChildAlertDTO;
import com.safetynetalerts.safetynet.service.ChildAlertService;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

	@Autowired
	private ChildAlertService childAlertService;
	
	@GetMapping
	public ResponseEntity<List<ChildAlertDTO>> getChildrenByAddress(@RequestParam("address") String address) {
		List<ChildAlertDTO> children = childAlertService.getChildrenByAdress(address);
		return ResponseEntity.ok(children);
	}
}
