package com.example.strava.service;

import com.example.strava.dao.ChallengeRepository;
import com.example.strava.dao.UserChallengeRepository;
import com.example.strava.dao.UserRepository;
import com.example.strava.entity.Challenge;
import com.example.strava.entity.TrainingSession;
import com.example.strava.entity.User;
import com.example.strava.entity.UserChallenge;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

	private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final UserChallengeRepository userchallengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository, UserChallengeRepository userchallengeRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.userchallengeRepository = userchallengeRepository;
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
            
            UserChallenge userChallenge = new UserChallenge(user, challenge, 0);
            user.addAcceptedChallenge(userChallenge);
            
            challengeRepository.save(challenge);
            userRepository.save(user);
            userchallengeRepository.save(userChallenge);
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
        	
        	UserChallenge userChallenge = new UserChallenge(user, challenge, 0);
        	user.addAcceptedChallenge(userChallenge);
        	
        	userRepository.save(user);
        	userchallengeRepository.save(userChallenge);
            return challenge;
        } else {
            return null;
        }
    }

    // Get accepted challenges for a user
    public List<Challenge> getAcceptedChallenges(String userId, String token) {
    	 if (UserService.isTokenValid(userId, token)) {
             Optional<User> userOpt = userRepository.findByUserId(userId);
             return userOpt.map(User::getChallenges).orElse(new ArrayList<>());
         } else {
             return null;
         }
     }
    
    public Map<String, Integer> challengeStatus(String userId, String token) {
        Map<String, Integer> challengeStatus = new HashMap<>();

        if (UserService.isTokenValid(userId, token)) {
            Optional<User> userOpt = userRepository.findByUserId(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                for (UserChallenge userChallenge : user.getUserChallenges()) {
                    challengeStatus.put(userChallenge.getChallenge().getChallengeName(), userChallenge.getProgress());
                    }
                }
        return challengeStatus;
        }
        return null;
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}