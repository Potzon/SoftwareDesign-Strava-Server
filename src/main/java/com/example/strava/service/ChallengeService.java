package com.example.strava.service;

import com.example.strava.dto.ChallengeDTO;
import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.entity.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChallengeService {
    private final List<ChallengeDTO> challenges = new ArrayList<>();
    private final Map<String, Float> challengeStatus = new HashMap<>();
    
    // Create a new challenge
    public ChallengeDTO challenge(String userId, String token, String name, Date startDate, Date endDate,
                                        Integer targetTime, Float targetDistance, String sport) {
        // Default target values if empty
        if (targetTime == null) targetTime = 0;
        if (targetDistance == null) targetDistance = 0.0f;
        if(UserService.isTokenValid(userId, token)) {
        	String challengeId = generateToken();
            ChallengeDTO challenge = new ChallengeDTO(userId, challengeId, name, startDate, endDate, targetTime, targetDistance, sport);
            challenges.add(challenge);
            User loggedUser = UserService.activeSessions.get(token);
            loggedUser.addAcceptedChallenge(challenge);
            return challenge;
        } else {
            return null;
        }
    }

    // Download active challenges for a user
    public List<ChallengeDTO> challenges(Date startDate, Date endDate, String sport) {
    	return challenges.stream()
    	        .filter(challenge -> 
    	            (startDate == null || (challenge.getStartDate() != null && (challenge.getStartDate().equals(startDate) || challenge.getStartDate().after(startDate)))) &&
    	            (endDate == null || (challenge.getStartDate() != null && (challenge.getStartDate().equals(startDate) || challenge.getStartDate().before(endDate)))) &&
    	            (sport == null || (challenge.getSport() != null && challenge.getSport().equals(sport)))
    	        ).collect(Collectors.toList());
    }

    // Get a challenge by ID
    public ChallengeDTO getChallengeById(String challengeId) {
        return challenges.stream()
                .filter(challenge -> challenge.getChallengeId().equals(challengeId))
                .findFirst()
                .orElse(null);
    }

    // Accept a challenge by challengeId
    public ChallengeDTO challengeParticipant(String challengeId, String userId, String token) {
        ChallengeDTO challenge = getChallengeById(challengeId);
        User user = UserService.activeSessions.get(token);
        if (challenge == null) {
            throw new IllegalArgumentException("Challenge not found with ID: " + challengeId);
        }

        // Agregar el estado aceptado a la lista de desafíos aceptados
        if (UserService.isTokenValid(userId, token)) {
        	user.addAcceptedChallenge(challenge);
            return challenge;
        } else {
            return null;
        }
    }

    // Get accepted challenges for a user
    public List<ChallengeDTO> getAcceptedChallenges(String userId, String token) {
    	User loggedUser = UserService.activeSessions.get(token);
        if (UserService.isTokenValid(userId, token)) {
        	return loggedUser != null ? loggedUser.getAcceptedChallenges() : new ArrayList<>();
		}else {
			return null;
		}
        
    }
    public Map<String, Float> challengeStatus(String userId, String token) {
        User user = UserService.activeSessions.get(token);
        
        Map<String, Float> challengeStatus = new HashMap<>();
        if (UserService.isTokenValid(userId, token)) {
        	for (ChallengeDTO challenge : user.getAcceptedChallenges()) {
                Float total = 0.0f;
        
                for (TrainingSessionDTO session : user.getTrainingSessions()) {

                    if (session.getSport().equals(challenge.getSport())
                    		&& session.getStartDate().after(challenge.getStartDate())
                    		&& session.getStartDate().before(challenge.getEndDate())) {
                        total = total + session.getDistance();
                    }
                }
                total = total / challenge.getTargetDistance() * 100;
                challengeStatus.put(challenge.getChallengeName(), total);
            }
        }
        return challengeStatus;
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}