package com.safetynetalerts.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static final Logger logger= LogManager.getLogger(FirestationServiceImpl.class);

	@Autowired
	private FirestationRepository firestationRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	/**
	 *GET all firestations
	 */
	@Override
	public List<FirestationDTO> getAllFirestations() {
		logger.debug("Fetching all firestations from repository");
		
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		
		logger.debug("Converting firestations to DTOs.");
		return firestations.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * ADD firestation
	 */
	@Override
	public void addFirestation (FirestationDTO firestationDTO) {
		Firestation firestation = convertToEntity(firestationDTO);
		logger.debug("Adding firestation to repository. ", firestation);
		
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		firestations.add(firestation);
		firestationRepository.saveAllFirestations(firestations);
		
		logger.debug("Firestation added successfully: {} ", firestation);
	}
	
	/**
	 *UPDATED a firestation
	 */
	@Override
	public void updateFirestation(FirestationDTO firestationDTO) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		logger.debug("Updating firestation in repository: {}", firestationDTO);
		
		Optional<Firestation> existingFirestation = firestations.stream()
				.filter(fs -> fs.getAddress().equals(firestationDTO.getAddress())).findFirst();
		
		if(existingFirestation.isPresent()) {
			
			existingFirestation.get().setStation(firestationDTO.getStation());
			
			firestationRepository.saveAllFirestations(firestations);
			logger.debug("Firestation updated successfully: {}", firestationDTO);
			
		} else {
			logger.warn("Firestation not found with address: {}", firestationDTO.getAddress());
		}
		
	}
	
	
	/**
	 * DELETED a firestation
	 */
	@Override
	public void deleteFirestation(String address) {
		List<Firestation> firestations = firestationRepository.getAllFirestations();
		logger.debug("Deleting a firestation with address: {}", address);
		
		boolean removed = firestations.removeIf(fs -> fs.getAddress().equals(address));
		
		if (removed) {
			firestationRepository.saveAllFirestations(firestations);
			logger.debug("Deleted a firestation with address: {}", address);
		} else {
			logger.warn("No firestation found with address: {}", address);
		}
	}
	

	/**
	 * getting a list of persons covered by the corresponding firestation
	 */
	@Override
	public FirestationCoverageDTO getCoverageByStationNumber(int stationNumber) {
		logger.debug("Getting the coverage with station number: {}", stationNumber);
		
		List<Person> listOfPersons = personRepository.getAllPersons();
		logger.debug("Retrieved {} persons from repository.", listOfPersons.size());
		
		List<Firestation> listOfFirestations = firestationRepository.getAllFirestations();
		logger.debug("Retrieved {} firestations from repository.", listOfFirestations.size());
		
		String stationNumberStr = String.valueOf(stationNumber);
		List <String> coveredAddresses = listOfFirestations.stream()
				.filter(fs -> fs.getStation().equals(stationNumberStr))
				.map(Firestation::getAddress).collect(Collectors.toList());
		logger.debug("Found {} adresses covred by the station number {}", coveredAddresses.size(), stationNumber);
		
		List<Person> coveredPersons = listOfPersons.stream()
				.filter(person -> coveredAddresses
				.contains(person.getAddress()))
				.collect(Collectors.toList());
		logger.debug("Found {} persons covered by the station number {}", coveredPersons.size(), stationNumber);
		
		List<FirestationPersonDTO> personCoverageDTOs = coveredPersons.stream()
				.map(this::convertToFirestationPersonDTO)
				.collect(Collectors.toList());
		logger.debug("Converted covered persons to DTOs.");
		
		long childrenCount = coveredPersons.stream()
				.filter(person -> {
                    MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName());
                    if(medicalRecord == null) {
                    	logger.debug("No medical record found for person: {} {}", person.getFirstName(), person.getLastName());
                    	return false;
                    }
                    int age = getAge(medicalRecord.getBirthdate());
                    logger.debug("Person: {} {} is {} years old", person.getFirstName(), person.getLastName(), age);
                    
                    return age <= 18;
                })
				.count();
		logger.debug("Found {} children covered by station number {}", childrenCount, stationNumber);
		
		FirestationCoverageDTO coverageDTO = new FirestationCoverageDTO();
		coverageDTO.setPersons(personCoverageDTOs);
		coverageDTO.setNumberOfAdults(coveredPersons.size() - (int) childrenCount);
		coverageDTO.setNumberOfChildren((int)childrenCount);
		
		logger.debug("Returning coverageDTO: {}", coverageDTO);
		
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
