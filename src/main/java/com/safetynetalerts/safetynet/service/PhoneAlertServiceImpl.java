package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class PhoneAlertServiceImpl implements PhoneAlertService {

	@Autowired
	private FirestationRepository firestationRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<String> getPhoneNumbersByFirestation(int stationNumber) {
		List<String> coveredAddresses = firestationRepository.getAllFirestations().stream()
				.filter(fs -> fs.getStation().equals(String.valueOf(stationNumber)))
				.map(fs -> fs.getAddress()).collect(Collectors.toList());
		
		List<String> phoneNumbers = personRepository.getAllPersons().stream()
				.filter(person -> coveredAddresses.contains(person.getAddress()))
				.map(person -> person.getPhone())
				.distinct()
				.collect(Collectors.toList());
		
		return phoneNumbers;
	}

	
}
