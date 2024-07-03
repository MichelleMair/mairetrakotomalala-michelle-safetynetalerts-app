package com.safetynetalerts.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.dto.PersonInfoDTO;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	
	private static final Logger logger= LogManager.getLogger(PersonInfoServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	@Override
	public List<PersonInfoDTO> getPersonsInfoByLastName(String lastName) {
		List<PersonInfoDTO> personsInfo = personRepository.getAllPersons().stream()
				.filter(person -> person.getLastName().equals(lastName))
				.map(this::convertToPersonInfoDTO).collect(Collectors.toList());
		return personsInfo;
	}
	
	private PersonInfoDTO convertToPersonInfoDTO(Person person) {
		PersonInfoDTO personInfoDTO = new PersonInfoDTO();
		
		personInfoDTO.setFirstName(person.getFirstName());
		personInfoDTO.setLastName(person.getLastName());
		personInfoDTO.setAddress(person.getAddress());
		personInfoDTO.setAge(getAge(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate()));
		personInfoDTO.setEmail(person.getEmail());
		personInfoDTO.setMedications(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getMedications());
		personInfoDTO.setAllergies(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getAllergies());
		
		return personInfoDTO;
	}

	private int getAge(String birthdate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDate = LocalDate.parse(birthdate, formatter);
		LocalDate currentDate = LocalDate.now();
		if ((birthDate != null) && (currentDate != null)) {
			return Period.between(birthDate, currentDate).getYears();
		} else {
			return 0;
		}
	}
	
}
