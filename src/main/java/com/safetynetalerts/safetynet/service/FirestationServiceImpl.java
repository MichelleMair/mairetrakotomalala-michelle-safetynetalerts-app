package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.repository.FirestationRepository;

@Service
public class FirestationServiceImpl implements FirestationService {

	@Autowired
	private FirestationRepository firestationRepository;
	
	@Override
	public List<FirestationDTO> getAllFirestations() {
		return firestationRepository.getAllFirestations().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public void addFirestation (FirestationDTO firestationDTO) {
		Firestation firestation = convertToEntity(firestationDTO);
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		firestations.add(firestation);
		firestationRepository.saveAllFirestations(firestations);
	}
	
	@Override
	public void updateFirestation(FirestationDTO firestationDTO) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		Optional<Firestation> existingFirestation = firestations.stream()
				.filter(fs -> fs.getAddress().equals(firestationDTO.getAddress())).findFirst();
		
		existingFirestation.ifPresent(fs -> fs.setStation(firestationDTO.getStation()));
		
		firestationRepository.saveAllFirestations(firestations);
	}
	
	@Override
	public void deleteFirestation(String address) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		firestations.removeIf(fs -> fs.getAddress().equals(address));
		firestationRepository.saveAllFirestations(firestations);
	}
	
	private FirestationDTO convertToDTO(Firestation firestation) {
		FirestationDTO dto = new FirestationDTO();
		
		dto.setAddress(firestation.getAddress());
		dto.setStation(firestation.getStation());
		
		return dto;
	}
	
	private Firestation convertToEntity(FirestationDTO dto) {
		Firestation firestation = new Firestation();
		
		firestation.setAddress(dto.getAddress());
		firestation.setStation(dto.getStation());
		
		return firestation;
	}
}
