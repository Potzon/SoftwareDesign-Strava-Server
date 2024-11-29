package com.example.strava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.strava.dao.GoogleUserRepository;
import com.example.strava.entity.Credentials;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GoogleUserService {

    @Autowired
    private GoogleUserRepository googleUserRepository;

    private static final Map<String, String> activeTokens = new ConcurrentHashMap<>();
    public boolean isRegistered(String email) {
       Credentials credentials = googleUserRepository.findByEmail(email);  
        return credentials != null; 
    }

    public String login(String email, String password) {
       Credentials credentials = googleUserRepository.findByEmail(email);

        if (credentials == null) {
            throw new RuntimeException("User not found");
        }

        if (!validatePassword(credentials, password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = generateToken();
        saveToken(credentials.getEmail(), token);
        
        return token;
    }

    private boolean validatePassword(Credentials user, String password) {
        return user.getPassword().equals(password);
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

    private void saveToken(String email, String token) {
        activeTokens.put(email, token);
    }

    public String getToken(String email) {
        return activeTokens.get(email); 
    }
}
