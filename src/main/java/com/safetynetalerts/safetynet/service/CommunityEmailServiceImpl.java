package com.safetynetalerts.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class CommunityEmailServiceImpl implements CommunityEmailService{

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<String> getEmailsByCity(String city) {
		List<String> emailsByCity = personRepository.getAllPersons().stream()
				.filter(person -> person.getCity().equals(city))
				.map(person -> person.getEmail()).distinct()
				.collect(Collectors.toList());
		return emailsByCity;
	}

}
