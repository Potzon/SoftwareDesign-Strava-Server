package com.example.strava.service;

import com.example.strava.dao.ChallengeRepository;
import com.example.strava.dao.UserRepository;
import com.example.strava.entity.Challenge;
import com.example.strava.entity.TrainingSession;
import com.example.strava.entity.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

	private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }
	
    
    // Create a new challenge
    public Challenge challenge(String userId, String token, String name, Date startDate, Date endDate,
                                        Integer targetTime, Float targetDistance, String sport) {
        // Default target values if empty
        if (targetTime == null) targetTime = 0;
        if (targetDistance == null) targetDistance = 0.0f;
        if(UserService.isTokenValid(userId, token)) {
        	String challengeId = generateToken();
            Challenge challenge = new Challenge(userId, challengeId, name, startDate, endDate, targetTime, targetDistance, sport);

            Optional<User> userOpt = userRepository.findByUserId(userId);
            User user = userOpt.get();
            

            user.addAcceptedChallenge(challenge);
            challengeRepository.save(challenge);
            userRepository.save(user);
            
            return challenge;
        } else {
            return null;
        }
    }

    // Download active challenges for a user
    public List<Challenge> challenges(Date startDate, Date endDate, String sport) {
    	
    	
    	return challengeRepository.findAll().stream()
                .filter(challenge ->
                    (startDate == null || (challenge.getStartDate() != null && (challenge.getStartDate().equals(startDate) || challenge.getStartDate().after(startDate)))) &&
                    (endDate == null || (challenge.getEndDate() != null && (challenge.getEndDate().equals(endDate) || challenge.getEndDate().before(endDate)))) &&
                    (sport == null || (challenge.getSport() != null && challenge.getSport().equals(sport)))
                ).collect(Collectors.toList());
        }

    // Get a challenge by ID
    public Challenge getChallengeById(String challengeId) {
    	 return challengeRepository.findById(challengeId).orElse(null);
    }

    // Accept a challenge by challengeId
    public Challenge challengeParticipant(String challengeId, String userId, String token) {
        Challenge challenge = getChallengeById(challengeId);
        User user = UserService.activeSessions.get(token);
        if (challenge == null) {
            throw new IllegalArgumentException("Challenge not found with ID: " + challengeId);
        }

        // Agregar el estado aceptado a la lista de desafíos aceptados
        if (UserService.isTokenValid(userId, token)) {
        	user.addAcceptedChallenge(challenge);
        	userRepository.save(user);
            return challenge;
        } else {
            return null;
        }
    }

    // Get accepted challenges for a user
    public List<Challenge> getAcceptedChallenges(String userId, String token) {
    	 if (UserService.isTokenValid(userId, token)) {
             Optional<User> userOpt = userRepository.findByUserId(userId);
             return userOpt.map(User::getAcceptedChallenges).orElse(new ArrayList<>());
         } else {
             return null;
         }
     }
    
    public Map<String, Float> challengeStatus(String userId, String token) {
        Map<String, Float> challengeStatus = new HashMap<>();

        if (UserService.isTokenValid(userId, token)) {
            Optional<User> userOpt = userRepository.findByUserId(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                for (Challenge challenge : user.getAcceptedChallenges()) {
                    Float total = 0.0f;

                    // Buscar sesiones de entrenamiento que coincidan con el desafío
                    for (TrainingSession session : user.getTrainingSessions()) {
                        if (session.getSport().equals(challenge.getSport()) &&
                                session.getStartDate().after(challenge.getStartDate()) &&
                                session.getStartDate().before(challenge.getEndDate())) {
                            total += session.getDistance();
                        }
                    }

                    // Calcular porcentaje de avance
                    total = total / challenge.getTargetDistance() * 100;
                    challengeStatus.put(challenge.getChallengeName(), total);
                }
            }
        }
        return challengeStatus;
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}