package com.safetynetalerts.safetynet.service;

import java.util.List;

public interface PhoneAlertService {

	List<String> getPhoneNumbersByFirestation(int stationNumber);
}
