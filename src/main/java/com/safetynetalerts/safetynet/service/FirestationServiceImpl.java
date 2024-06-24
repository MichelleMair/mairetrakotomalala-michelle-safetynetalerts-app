package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.repository.FirestationRepository;

@Service
public class FirestationServiceImpl implements FirestationService {

	@Autowired
	private FirestationRepository firestationRepository;
	
	@Override
	public List<Firestation> getAllFirestations() {
		return firestationRepository.getAllFirestations();
	}
	
	@Override
	public void addFirestation (Firestation firestation) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		firestations.add(firestation);
		firestationRepository.saveAllFirestations(firestations);
	}
	
	@Override
	public void updateFirestation(Firestation firestation) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		Optional<Firestation> existingFirestation = firestations.stream()
				.filter(fs -> fs.getAddress().equals(firestation.getAddress())).findFirst();
		
		existingFirestation.ifPresent(fs -> fs.setStation(firestation.getStation()));
		
		firestationRepository.saveAllFirestations(firestations);
	}
	
	@Override
	public void deleteFirestation(String address) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		firestations.removeIf(fs -> fs.getAddress().equals(address));
		firestationRepository.saveAllFirestations(firestations);
	}
}
