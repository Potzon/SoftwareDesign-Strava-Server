package com.example.strava.service;

import com.example.strava.entity.TrainingSession;
import com.example.strava.entity.User;
import com.example.strava.entity.UserChallenge;

import jakarta.transaction.Transactional;

import com.example.strava.dao.UserRepository;
import com.example.strava.dao.TrainingSessionRepository;
import com.example.strava.dao.UserChallengeRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainingService {
	
	private final TrainingSessionRepository trainingSessionRepository;
    private final UserRepository userRepository;
    private final UserChallengeRepository userChallengeRepository;
    
    public TrainingService(TrainingSessionRepository trainingSessionRepository, UserRepository userRepository, UserChallengeRepository userChallengeRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.userRepository = userRepository;
        this.userChallengeRepository = userChallengeRepository;
    }
    @Transactional
    public TrainingSession session(String userId, String token, String title, String sport, Float distance, Date startDate, Float duration) {
      	if (UserService.isTokenValid(userId, token)) {
      		User loggeduser = UserService.activeSessions.get(token);
      		String sessionId = generateToken();
      		TrainingSession session = new TrainingSession(sessionId, loggeduser, title, sport, distance, startDate, duration);
      		
      	loggeduser.addSessionToUser(session);
      	
      	//Update progress
      	List<UserChallenge> userChallenges = loggeduser.getAcceptedChallenges();
		for (UserChallenge userChallenge : userChallenges) {
			if (userChallenge.getChallenge().getSport().equals(sport)) {
				int newProgress = 0;
				float totalDistance = 0;
				for (TrainingSession session2: loggeduser.getTrainingSessions()) {
		    		if(session2.getSport().equals(userChallenge.getChallenge().getSport()) 
		    		&& !session2.getStartDate().before(userChallenge.getChallenge().getStartDate()) 
		    		&& !session2.getStartDate().after(userChallenge.getChallenge().getEndDate())) {
		    			totalDistance += session.getDistance();
		    		}
		    	}
				newProgress = (int) ((totalDistance/userChallenge.getChallenge().getTargetDistance())*100);
				userChallenge.setProgress(newProgress);
				userChallengeRepository.save(userChallenge);
				
			}
		}
      	
      	userRepository.save(loggeduser);
      	trainingSessionRepository.save(session);
      	
        return session;
     } else {
         return null;
     }
    }

    public List<TrainingSession> sessions(String token, Date startDate, Date endDate) {
    	System.err.println("Token: "+ token + " UserId: "+ UserService.activeSessions.get(token).getUserId());
        String userId = UserService.activeSessions.get(token).getUserId();
        if (!UserService.isTokenValid(userId, token)) {
            return new ArrayList<>();
        }
        
        // Obtener el usuario
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isEmpty()) {
            return new ArrayList<>();
        }
        User loggedUser = userOpt.get();
        
        // Establecer fechas por defecto si son nulas
        Date defaultStartDate = startDate != null ? startDate : new Date(0); 
        Date defaultEndDate = endDate != null ? endDate : new Date(Long.MAX_VALUE); 
        
        List<TrainingSession> sessions = trainingSessionRepository.findByUser_UserIdAndStartDateBetween(loggedUser.getUserId(), defaultStartDate, defaultEndDate);
        
        return sessions; 
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
