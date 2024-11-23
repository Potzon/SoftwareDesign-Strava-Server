package com.example.strava.service;

import com.example.strava.entity.TrainingSession;
import com.example.strava.entity.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    public TrainingSession session(String userId, String token, String title, String sport, Float distance, Date startDate, Float duration) {
      	if (UserService.isTokenValid(userId, token)) {
      		String sessionId = generateToken();
      		TrainingSession session = new TrainingSession(sessionId, title, sport, distance, startDate, duration);
      		User loggeduser = UserService.activeSessions.get(token);
      	loggeduser.addSessionToUser(session);
        return session;
     } else {
         return null;
     }
    }

    public List<TrainingSession> sessions(String token, Date startDate, Date endDate) {
    	User loggeduser = UserService.activeSessions.get(token);
    	
        return loggeduser.getTrainingSessions().stream()
        		.filter(session -> 
        		(startDate == null || (session.getStartDate() != null && (session.getStartDate().equals(startDate) || session.getStartDate().after(startDate)))) &&
	            (endDate == null || (session.getStartDate() != null && (session.getStartDate().equals(startDate) || session.getStartDate().before(endDate))))
            ).collect(Collectors.toList());
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
