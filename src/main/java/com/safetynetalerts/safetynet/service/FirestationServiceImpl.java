package com.safetynetalerts.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.FirestationPersonDTO;
import com.safetynetalerts.safetynet.dto.FirestationCoverageDTO;
import com.safetynetalerts.safetynet.dto.FirestationDTO;
import com.safetynetalerts.safetynet.dto.PersonDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class FirestationServiceImpl implements FirestationService {

	@Autowired
	private FirestationRepository firestationRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
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
	

	@Override
	public FirestationCoverageDTO getCoverageByStationNumber(int stationNumber) {
		List<Person> listOfPersons = personRepository.getAllPersons();
		List<Firestation> listOfFirestations = firestationRepository.getAllFirestations();
		
		List <String> coveredAddresses = listOfFirestations.stream()
				.filter(fs -> fs.getStation().equals(String.valueOf(stationNumber)))
				.map(Firestation::getAddress).collect(Collectors.toList());
		
		List<Person> coveredPersons = listOfPersons.stream()
				.filter(person -> coveredAddresses
				.contains(person.getAddress()))
				.collect(Collectors.toList());
		
		List<FirestationPersonDTO> personCoverageDTOs = coveredPersons.stream()
				.map(this::convertToFirestationPersonDTO)
				.collect(Collectors.toList());
		
		long childrenCount = coveredPersons.stream()
				.filter(person -> {
                    MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName());
                    return medicalRecord != null && getAge(medicalRecord.getBirthdate()) <= 18;
                })
				.count();
		
		FirestationCoverageDTO coverageDTO = new FirestationCoverageDTO();
		coverageDTO.setPersons(personCoverageDTOs);
		coverageDTO.setNumberOfAdults(coveredPersons.size() - (int) childrenCount);
		coverageDTO.setNumberOfChildren((int)childrenCount);
		
		return coverageDTO;
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
	
	private FirestationPersonDTO convertToFirestationPersonDTO(Person person) {
		FirestationPersonDTO firestationPersonDTO = new FirestationPersonDTO();
		firestationPersonDTO.setFirstName(person.getFirstName());
		firestationPersonDTO.setLastName(person.getLastName());
		firestationPersonDTO.setAddress(person.getAddress());
		firestationPersonDTO.setPhone(person.getPhone());
		return firestationPersonDTO;
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
