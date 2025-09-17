package com.gamestudio.game.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserValidationService {

    private final RestTemplate restTemplate;
    private final String USER_SERVICE_URL = "http://localhost:8081/users"; // адрес user-service

    public UserValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean userExists(Long userId) {
        try {
            restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Object.class);
            return true;
        } catch (RestClientException e) {
            return false;
        }
    }
}