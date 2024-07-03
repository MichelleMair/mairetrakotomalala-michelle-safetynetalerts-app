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

import com.safetynetalerts.safetynet.dto.ChildAlertDTO;
import com.safetynetalerts.safetynet.dto.HouseholdMemberDTO;
import com.safetynetalerts.safetynet.model.Person;
import com.safetynetalerts.safetynet.model.MedicalRecord;
import com.safetynetalerts.safetynet.repository.MedicalRecordRepository;
import com.safetynetalerts.safetynet.repository.PersonRepository;

@Service
public class ChildAlertServiceImpl implements ChildAlertService {
	
	private static final Logger logger= LogManager.getLogger(ChildAlertServiceImpl.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Override
	public List<ChildAlertDTO> getChildrenByAdress(String address) {
		
		List<Person> personsAtSameAddress = personRepository.getAllPersons().stream()
				.filter(person -> person.getAddress().equals(address))
				.collect(Collectors.toList());
		
		List<ChildAlertDTO> children = personsAtSameAddress.stream().map(person -> {
			MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName());
			int age = getAge(medicalRecord.getBirthdate());
			if (age <= 18) {
				ChildAlertDTO childAlertDTO = new ChildAlertDTO();
				childAlertDTO.setFirstName(person.getFirstName());
				childAlertDTO.setLastName(person.getLastName());
				childAlertDTO.setAge(age);
				List<HouseholdMemberDTO> householdMembers = personsAtSameAddress.stream()
						.filter(member -> !member.equals(person))
						.map(member -> {
							HouseholdMemberDTO householdMemberDTO = new HouseholdMemberDTO();
							householdMemberDTO.setFirstName(member.getFirstName());
							householdMemberDTO.setLastName(member.getLastName());
							return householdMemberDTO;
						})
				.collect(Collectors.toList());
				childAlertDTO.setHouseholdMembers(householdMembers);
				return childAlertDTO;
			} else {
				return null;
			}
		})
		.filter(child -> child != null).collect(Collectors.toList());
		
		return children;
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
