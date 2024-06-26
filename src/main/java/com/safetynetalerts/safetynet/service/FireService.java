package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.FireDTO;

public interface FireService {

	List<FireDTO> getPersonsByAddress (String address);
}
