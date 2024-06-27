package com.safetynetalerts.safetynet.service;

import java.util.List;

public interface CommunityEmailService {

	List<String> getEmailsByCity(String city);
}
