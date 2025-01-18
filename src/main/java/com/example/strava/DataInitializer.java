package com.example.strava;

import com.example.strava.service.UserService;
import com.example.strava.service.TrainingService;
import com.example.strava.dao.ChallengeRepository;
import com.example.strava.dao.TrainingSessionRepository;
import com.example.strava.dao.UserChallengeRepository;
import com.example.strava.dao.UserRepository;
import com.example.strava.entity.User;
import com.example.strava.entity.UserChallenge;
import com.example.strava.entity.Challenge;
import com.example.strava.entity.TrainingSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    @Transactional
    CommandLineRunner initData(UserRepository userRepository, TrainingSessionRepository trainingSessionRepository,
                                ChallengeRepository challengeRepository, UserChallengeRepository userChallengeRepository) {
        return args -> {
            // Database is already initialized
            if (userRepository.count() > 0) {
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse("2004-09-04", formatter);
            Date date = java.sql.Date.valueOf(localDate);

            // Users
            User batman = new User("1", "Godtzon", "user1@example.com", "password123", date, 60, 188, 160.5f, 69.5f);
            User spiderman = new User("2", "Currante", "user2@example.com", "password456", date, 97, 190, 120.0f, 60.0f);
            User superman = new User("3", "Adama Bouro", "user3@example.com", "password789", date, 67, 160, 200.0f, 100.0f);
            User wonderWoman = new User("4", "Diana", "user4@example.com", "password000", date, 88, 177, 180.0f, 90.0f);
            User flash = new User("5", "Barry", "user5@example.com", "password111", date, 110, 199, 100.0f, 55.0f);
            User aquaman = new User("6", "Arthur", "user6@example.com", "password222", date, 55, 150, 100.0f, 50.0f);


            // Save users
            userRepository.saveAll(List.of(batman, spiderman, superman, wonderWoman, flash, aquaman));
            logger.info("Users saved!");

            // Establish the dates
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date challengeEndDate = calendar.getTime();

            // Training sessions
            TrainingSession session1 = new TrainingSession("11", batman, "Morning Run", "Running", 5.0f, currentDate, 2.5f);
            TrainingSession session2 = new TrainingSession("12", spiderman, "Evening Cycle", "Cycling", 20.0f, currentDate, 4f);
            TrainingSession session3 = new TrainingSession("13", superman, "Swimming Session", "Swimming", 1.5f, currentDate, 1f);
            TrainingSession session4 = new TrainingSession("14", wonderWoman, "Mountain Hike", "Hiking", 10.0f, currentDate, 5f);
            TrainingSession session5 = new TrainingSession("15", flash, "Speed Run", "Running", 15.0f, currentDate, 3f);
            TrainingSession session6 = new TrainingSession("16", aquaman, "Ocean Dive", "Swimming", 2.0f, currentDate, 1.5f);

            // Save training sessions
            trainingSessionRepository.saveAll(List.of(session1, session2, session3, session4, session5, session6));
            logger.info("Training sessions saved!");

            // Challenges
            Challenge challenge1 = new Challenge(batman.getUserId(), "21", "Morning Run", currentDate, challengeEndDate, 50, 5.0f, "Running");
            Challenge challenge2 = new Challenge(spiderman.getUserId(), "22", "Evening Cycle", currentDate, challengeEndDate, 20, 4f, "Cycling");
            Challenge challenge3 = new Challenge(superman.getUserId(), "23", "Swimming Session", currentDate, challengeEndDate, 23, 1f, "Swimming");
            Challenge challenge4 = new Challenge(wonderWoman.getUserId(), "24", "Mountain Hike", currentDate, challengeEndDate, 30, 10.0f, "Hiking");
            Challenge challenge5 = new Challenge(flash.getUserId(), "25", "Speed Run", currentDate, challengeEndDate, 60, 15.0f, "Running");
            Challenge challenge6 = new Challenge(aquaman.getUserId(), "26", "Ocean Dive", currentDate, challengeEndDate, 10, 2.0f, "Swimming");

            // Save challenges
            challengeRepository.saveAll(List.of(challenge1, challenge2, challenge3, challenge4, challenge5, challenge6));
            logger.info("Challenges saved!");

            // Progresses
            UserChallenge userChallenge1 = new UserChallenge(batman, challenge1, 19);
            userChallengeRepository.save(userChallenge1);
            UserChallenge userChallenge2 = new UserChallenge(batman, challenge2, 23);
            userChallengeRepository.save(userChallenge2);
            UserChallenge userChallenge3 = new UserChallenge(superman, challenge3, 0);
            userChallengeRepository.save(userChallenge3);
            UserChallenge userChallenge4 = new UserChallenge(wonderWoman, challenge4, 15);
            userChallengeRepository.save(userChallenge4);
            UserChallenge userChallenge5 = new UserChallenge(flash, challenge5, 45);
            userChallengeRepository.save(userChallenge5);
            UserChallenge userChallenge6 = new UserChallenge(aquaman, challenge6, 5);
            userChallengeRepository.save(userChallenge6);

            // Save progresses
            logger.info("Progress saved!");

            System.out.println("Data initialization completed!");
        };
    }
}
