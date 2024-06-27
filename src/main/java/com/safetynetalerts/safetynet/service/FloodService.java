package com.safetynetalerts.safetynet.service;

import java.util.List;

import com.safetynetalerts.safetynet.dto.FloodDTO;

public interface FloodService {

	List<FloodDTO> getFloodInformation(List<Integer> stationNumbers);
}
