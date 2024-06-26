package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.ChildAlertDTO;

public interface ChildAlertService {

	List<ChildAlertDTO> getChildrenByAdress(String address);
}
