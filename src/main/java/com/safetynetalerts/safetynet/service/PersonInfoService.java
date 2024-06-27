package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.PersonInfoDTO;

public interface PersonInfoService {

	List<PersonInfoDTO> getPersonsInfoByLastName(String lastName);
}
