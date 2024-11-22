package com.example.strava;

import com.example.strava.dto.UserDTO;
import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.dto.ChallengeDTO;
import com.example.strava.service.UserService;
import com.example.strava.service.TrainingService;
import com.example.strava.service.ChallengeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(UserService userService, TrainingService trainingService, ChallengeService challengeService) {
        return args -> {
            // Crear algunos usuarios
            UserDTO john = new UserDTO("user123", "John Doe", "johndoe@example.com", 75, 175, 190f, 60f);
            UserDTO jane = new UserDTO("user456", "Jane Smith", "janesmith@example.com", 65, 165, 180f, 55f);
            UserDTO alice = new UserDTO("user789", "Alice Johnson", "alicej@example.com", 70, 168, 185f, 58f);

            userService.registerUser(john);
            userService.registerUser(jane);
            userService.registerUser(alice);
            logger.info("Usuarios iniciales guardados!");

            // Crear algunas sesiones de entrenamiento
            TrainingSessionDTO morningRun = new TrainingSessionDTO("session001", "Morning Run", "Running", 5.0f, new Date(), "30m");
            TrainingSessionDTO eveningCycle = new TrainingSessionDTO("session002", "Evening Cycle", "Cycling", 20.0f, new Date(), "1h 15m");
            TrainingSessionDTO swimming = new TrainingSessionDTO("session003", "Swimming Session", "Swimming", 1.5f, new Date(), "45m");

            trainingService.addSession(morningRun);
            trainingService.addSession(eveningCycle);
            trainingService.addSession(swimming);
            logger.info("Sesiones de entrenamiento iniciales guardadas!");

            // Inicializar fecha de fin para los desafíos
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 7); // 7 días a partir de hoy
            Date challengeEndDate = calendar.getTime();

            // Crear algunos desafíos
            ChallengeDTO run10k = new ChallengeDTO("challenge001", "user123", "Run 10K", new Date(), challengeEndDate, 60, 10.0f, "Running", "active");
            ChallengeDTO cycle50k = new ChallengeDTO("challenge002", "user456", "Cycle 50K", new Date(), challengeEndDate, 120, 50.0f, "Cycling", "active");
            ChallengeDTO swim2k = new ChallengeDTO("challenge003", "user789", "Swim 2K", new Date(), challengeEndDate, 80, 2.0f, "Swimming", "active");

            challengeService.addChallenge(run10k);
            challengeService.addChallenge(cycle50k);
            challengeService.addChallenge(swim2k);
            logger.info("Desafíos iniciales guardados!");
        };
    }
}
