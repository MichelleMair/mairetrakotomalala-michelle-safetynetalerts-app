package com.safetynetalerts.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.safetynet.dto.FireDTO;
import com.safetynetalerts.safetynet.dto.FirestationPersonDTO;
import com.safetynetalerts.safetynet.dto.FloodDTO;
import com.safetynetalerts.safetynet.model.Firestation;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.repository.FirestationRepository;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class FloodServiceImpl implements FloodService{
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private FirestationRepository firestationRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Override
	public List<FloodDTO> getFloodInformation(List<Integer> stationNumbers) {
		List <String> coveredAddresses = getCoveredAddresses(stationNumbers);
		
		List<FloodDTO> residentsInfoForGivenStationNumber = coveredAddresses.stream()
				.map(this::createFloodDTO)
				.collect(Collectors.toList());		
	return residentsInfoForGivenStationNumber;
	}
	
	/**
	 * @param stationNumbers
	 * @return a list of covered addresses by station number
	 */
	private List<String> getCoveredAddresses(List<Integer> stationNumbers) {
		return firestationRepository.getAllFirestations().stream()
				.filter(fs -> stationNumbers.contains(Integer.parseInt(fs.getStation())))
				.map(fs -> fs.getAddress())
				.collect(Collectors.toList());
	}
	
	
	/**
	 * @param address
	 * @return new object floodDTO
	 */
	private FloodDTO createFloodDTO (String address) {
		List<FireDTO> residents = getResidentsByAddress(address);
		FloodDTO floodDTO = new FloodDTO();
		floodDTO.setAddress(address);
		floodDTO.setResidents(residents);
		
		return floodDTO;
	}
	
	
	/**
	 * @param address
	 * @return a list of objects "fireDTO" which represents residents for an address
	 */
	private List<FireDTO> getResidentsByAddress(String address) {
		 
		List<FireDTO> residents = personRepository.getAllPersons().stream()
				.filter(person -> person.getAddress().equals(address))
				.map(person -> convertToFireDTO(person, getStationNumberByAddress(address)))
				.collect(Collectors.toList());
		
		return residents;
	}
	
	
	/**
	 * @param address
	 * @return the station number for an address
	 */
	private String getStationNumberByAddress(String address) {
		String stationNumber = firestationRepository.getAllFirestations().stream()
				.filter(fs -> fs.getAddress().equals(address))
				.map(fs -> fs.getStation()).findFirst().orElse("");
		return stationNumber;
	}
	
	
	private FireDTO convertToFireDTO(Person person, String stationNumber) {
		FireDTO fireDTO = new FireDTO();
		fireDTO.setFirstName(person.getFirstName());
		fireDTO.setLastName(person.getLastName());
		fireDTO.setPhone(person.getPhone());
		fireDTO.setAge(getAge(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate()));
		fireDTO.setMedications(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getMedications());
		fireDTO.setAllergies(medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getAllergies());
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
