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
    public ResponseEntity<Map<String, String>> logout(
    		@Parameter(name = "userId", description = "Id of the user", required = true, example = "3")
    		@PathVariable("userId") String userId, 
    		@RequestBody String token) {
    	
    	boolean isLoggedOut = (userService.logout(userId, token).equals("Logout successful"));
        String validation = isLoggedOut ? "Logout successful" : "Logout failed";
        return new ResponseEntity<>(Map.of("validation", validation), HttpStatus.OK);
    }

    // Creating a training session
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
    @PostMapping("/users/{userId}/session")
    public ResponseEntity<TrainingSessionDTO> session(
    		@PathVariable("userId") String userId,
    		@RequestBody Map<String, TrainingSessionDTO> data) {
    	
    	String token = data.keySet().stream().findFirst().orElse(null);
    	TrainingSessionDTO sessionData = data.get(token);

    	TrainingSessionDTO session = trainingService.session(userId, token, sessionData.getTitle(), sessionData.getSport(), sessionData.getDistance(), sessionData.getStartDate(),sessionData.getDuration());
    	return new ResponseEntity<>(session, HttpStatus.CREATED);
    }
    
    
    // Querying Training Sessions 
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
    public ResponseEntity<List<TrainingSessionDTO>> sessions(
    		@PathVariable("userId") String userId,
    		@RequestBody String token,
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<TrainingSessionDTO> sessions = trainingService.sessions(token, startDate, endDate);
        if (sessions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    // Set Up a Challenge
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
    @PostMapping("/users/{userId}/challenge")
    public ResponseEntity<ChallengeDTO> challenge(
    		@PathVariable("userId") String userId,
    		@RequestBody Map<String, ChallengeDTO> data) { 
    	String token = data.keySet().stream().findFirst().orElse(null);
    	ChallengeDTO challengeData = data.get(token);

    	ChallengeDTO challenge = challengeService.challenge(userId, token, challengeData.getChallengeName(), challengeData.getStartDate(), challengeData.getEndDate(),challengeData.getTargetTime(), challengeData.getTargetDistance(), challengeData.getSport());
    	
    	return new ResponseEntity<>(challenge, HttpStatus.CREATED);
    }

    // Download Active Challenges 
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
    @GetMapping("/challenges")
    public ResponseEntity<List<ChallengeDTO>> challenges(
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
    		@RequestParam(name = "sport",required = false) String sport) {
        List<ChallengeDTO> challenges = challengeService.challenges(startDate, endDate, sport);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    // Accept Challenge 
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
    public ResponseEntity<List<ChallengeDTO>> challengeParticipant(
    		@PathVariable("userId") String userId, 
    		@PathVariable("challengeId") String challengeId,
    		@RequestBody String token) {
        challengeService.challengeParticipant(challengeId, userId, token);
        List<ChallengeDTO> challenges = challengeService.getAcceptedChallenges(userId, token);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    // Querying Accepted Challenges Status 
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
    		@PathVariable("userId") String userId,
    		@RequestBody String token) {
        Map<String, Float> status = challengeService.challengeStatus(userId, token);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}


