package com.example.strava.facade;

import com.example.strava.entity.User;
import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.dto.ChallengeDTO;
import com.example.strava.service.UserService;
import com.example.strava.service.TrainingService;
import com.example.strava.service.ChallengeService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/strava")
public class CentralController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TrainingService trainingService;
    
    @Autowired
    private ChallengeService challengeService;
    
    
    @Operation(
    		summary = "Register a new User account",
    		description = "Returns the details of the user account created",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: USer created successfully"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user.getEmail(), user.getPassword(), user.getName(), user.getBirthdate(), user.getWeight(), user.getHeight(), user.getMaxHeartRate(), user.getRestHeartRate());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    // Login - POST /login
    @Operation(
    		summary = "Login a user account",
    		description = "Returns the token for the user account logged in",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: User logged in successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Arguments not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        String token = userService.login(email, password);
        String validation = (token != null) ? "Login successful" : "Login failed";
        return new ResponseEntity<>(Map.of("token", token, "validation", validation), HttpStatus.OK);
    }
    
    // Logout - POST /logout
    @Operation(
    		summary = "Delete a User account",
    		description = "Returns the validation of the user account deleted",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: User deleted successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Token not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/users/{userId}/logout")
    public ResponseEntity<Map<String, String>> logout(@PathVariable String userId, @RequestBody String token) {
    	boolean isLoggedOut = (userService.logout(userId, token).equals("Logout successful"));
        String validation = isLoggedOut ? "Logout successful" : "Logout failed";
        return new ResponseEntity<>(Map.of("validation", validation), HttpStatus.OK);
    }

    // Creating a training session - POST /createSession
    @Operation(
    		summary = "Create a new Training Session",
    		description = "Returns the details of the training session created",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Session created successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Session data not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/users/{userId}/sessions")
    public ResponseEntity<TrainingSessionDTO> createSession(
    		@RequestParam(name = "token") String token,
    		@RequestParam(name = "title") String title,
    		@RequestParam(name = "sport") String sport,
    		@RequestParam(name = "distance") float distance,
    		@RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "duration") float duration) {
        TrainingSessionDTO session = trainingService.createSession(token, title, sport, distance, startDate, duration);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    // Querying Training Sessions - GET /getTrainingSessions
    @Operation(
    		summary = "Get the training sessions of a user",
    		description = "Returns the details of the users training sessions",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Training session details retrieved successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Token not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @GetMapping("/users/{userId}/sessions")
    public ResponseEntity<List<TrainingSessionDTO>> getTrainingSessions(
    		@RequestParam(name = "token") String token,
    		@RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<TrainingSessionDTO> sessions = trainingService.getTrainingSessions(token, startDate, endDate);
        if (sessions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    // Set Up a Challenge - POST /createChallenge
    @Operation(
    		summary = "Create a new Challenge",
    		description = "Returns the details of the created challenge",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Challenge created successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Token not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/users/{userId}/challenges")
    public ResponseEntity<ChallengeDTO> createChallenge(
    		@RequestParam(name = "token") String token,
    		@RequestParam(name = "name") String name,
    		@RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
    		@RequestParam(name = "targetTime",required = false) Integer targetTime,
    		@RequestParam(name = "targetDistance",required = false) Float targetDistance,
    		@RequestParam(name = "sport") String sport) { 
        ChallengeDTO challenge = challengeService.createChallenge(token, name, startDate, endDate, targetTime, targetDistance, sport);
        return new ResponseEntity<>(challenge, HttpStatus.CREATED);
    }

    // Download Active Challenges - GET /downloadChallenges
    @Operation(
    		summary = "Download the active challenges",
    		description = "Returns the details of the active challenges",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Article details retrieved successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: Article not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @GetMapping("/challenges?endDate=x")
    public ResponseEntity<List<ChallengeDTO>> downloadChallenges(
    		@RequestParam(name = "token") String token) {
        List<ChallengeDTO> challenges = challengeService.downloadChallenges(token);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    // Accept Challenge - POST /acceptChallenge
    @Operation(
    		summary = "Accept a challenge for a user",
    		description = "Returns the users accepted challenges",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Challenge accepted successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: ChallengeId not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: Challenge or user not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/users/{userId}/challenges/{challengeId}")
    public ResponseEntity<List<ChallengeDTO>> acceptChallenge(
    		@RequestParam(name = "token") String token, 
    		@RequestParam(name = "challengeId") String challengeId) {
        challengeService.acceptChallenge(challengeId, token);
        List<ChallengeDTO> challenges = challengeService.getAcceptedChallenges(token);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    // Querying Accepted Challenges Status - GET /challengeStatus
    @Operation(
    		summary = "Get the progress of the user on its accepted challenges",
    		description = "Returns the users accepted challenges names and its progresses",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: Challenge accepted successfully"),
    			@ApiResponse(responseCode = "400", description = "Bad Request: ChallengeId not supported"),
    			@ApiResponse(responseCode = "404", description = "Not Found: Challenge or user not found"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @GetMapping("/users/{userId}/challenges/status")
    public ResponseEntity<Map<String, Float>> challengeStatus(
    		@RequestParam(name = "token") String token) {
        Map<String, Float> status = challengeService.getChallengeStatus(token);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}


