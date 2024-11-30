package com.example.strava;

import com.example.strava.service.UserService;
import com.example.strava.service.TrainingService;
import com.example.strava.dao.ChallengeRepository;
import com.example.strava.dao.TrainingSessionRepository;
import com.example.strava.dao.UserRepository;
import com.example.strava.entity.User;
import com.example.strava.entity.Challenge;
import com.example.strava.entity.TrainingSession;
import com.example.strava.service.ChallengeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
//Euskadi bihotzean gaba heltzean
import java.util.List;
@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    @Transactional
    CommandLineRunner initData(UserRepository userRepository, TrainingSessionRepository trainingSessionRepository, ChallengeRepository challengeRepository) {
		return args -> {
			// Database is already initialized
            if (userRepository.count() > 0) {                
                return;
            }			
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse("2004-09-04", formatter);
            Date date = java.sql.Date.valueOf(localDate);
            
			// Create some users
			User batman = new User("1", "Godtzon", "user1@example.com", "password123", date, null, null, null, null);
			User spiderman = new User("2", "Currante", "user2@example.com", "password456", date, null, null, null, null);
			User superman = new User("3", "Adama Bouro","user3@example.com", "password789", date, null, null, null, null);
			
			// Save users
			userRepository.saveAll(List.of(batman, spiderman, superman));			
			logger.info("Users saved!");
			
			//Establish the dates
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			Date challengeEndDate = calendar.getTime();
			
			// Create some training sessions
			TrainingSession session1 = new TrainingSession("11", batman, "Morning Run", "Running", 5.0f, currentDate, 2.5f);
			TrainingSession session2 = new TrainingSession("12", spiderman, "Evening Cycle", "Cycling", 20.0f, currentDate, 4f);
			TrainingSession session3 = new TrainingSession("13", superman, "Swimming Session", "Swimming", 1.5f, currentDate, 1f);
			
			// Save training sessions
			trainingSessionRepository.saveAll(List.of(session1, session2, session3));
			logger.info("Training sessions saved!");
			
			
         	// Create some challenges
			Challenge challege1 = new Challenge(batman.getUserId(), "21", "Morning Run", currentDate, challengeEndDate, 50, 5.0f, "Running");
			
			Challenge challenge3 = new Challenge(superman.getUserId(), "22", "Swimming Session", currentDate, challengeEndDate, 23, 1f, "Swimming");
			Challenge challenge2 = new Challenge(spiderman.getUserId(), "23", "Evening Cycle", currentDate, challengeEndDate, 20, 4f,"Cycling");
          
			// Save challenges
			challengeRepository.saveAll(List.of(challege1, challenge2, challenge3));
			logger.info("Challenges saved!");
		};
	}
}
