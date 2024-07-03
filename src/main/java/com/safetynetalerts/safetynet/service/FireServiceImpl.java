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

import com.safetynetalerts.safetynet.dto.FireDTO;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class FireServiceImpl implements FireService {
	
	private static final Logger logger= LogManager.getLogger(FireServiceImpl.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private FirestationRepository firestationRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Override
	public List<FireDTO> getPersonsByAddress(String address) {
		List<Person> personsAtSameAddress = personRepository.getAllPersons().stream()
				.filter(person -> person.getAddress().equals(address))
				.collect(Collectors.toList());
		
		String stationNumber = firestationRepository.getAllFirestations().stream()
				.filter(fs -> fs.getAddress().equals(address))
				.map(fs -> fs.getStation())
				.findFirst().orElse("");
		
		return personsAtSameAddress.stream()
				.map(person -> convertToFireDTO(person, stationNumber))
				.collect(Collectors.toList());
	}
	
	private FireDTO convertToFireDTO(Person person, String stationNumber) {
		FireDTO fireDTO = new FireDTO();
		fireDTO.setFirstName(person.getFirstName());
		fireDTO.setLastName(person.getLastName());
		fireDTO.setPhone(person.getPhone());
		
		MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName());
		if(medicalRecord != null) {
			fireDTO.setAge(getAge(medicalRecord.getBirthdate()));
			fireDTO.setMedications(medicalRecord.getMedications());
			fireDTO.setAllergies(medicalRecord.getAllergies());
		}
		
		fireDTO.setStationNumber(stationNumber);
		
		return fireDTO;
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
