package com.example.strava.service;

import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.entity.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    public TrainingSessionDTO createSession(String token, String title, String sport, Float distance, Date startDate, Float duration) {
      	if (UserService.isTokenValid(token)) {
      		String sessionId = UUID.randomUUID().toString();
      		TrainingSessionDTO session = new TrainingSessionDTO(sessionId, title, sport, distance, startDate, duration);
      		User loggeduser = UserService.activeSessions.get(token);
      	loggeduser.addSessionToUser(session);
        return session;
     } else {
         return null;
     }
    }

    public List<TrainingSessionDTO> getTrainingSessions(String token, Date startDate, Date endDate) {
    	User loggeduser = UserService.activeSessions.get(token);
    	
        return loggeduser.getTrainingSessions().stream()
                .filter(session -> !session.getStartDate().before(startDate) && !session.getStartDate().after(endDate))
                .collect(Collectors.toList());
    }
}
