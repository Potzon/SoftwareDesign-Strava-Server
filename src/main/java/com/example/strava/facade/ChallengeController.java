package com.example.strava.facade;

import com.example.strava.dto.ChallengeDTO;
import com.example.strava.entity.Challenge;
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
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    

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
    public ResponseEntity<Challenge> challenge(
    		@PathVariable("userId") String userId,
    		@RequestBody ChallengeDTO dto) { 

    	Challenge challenge = challengeService.challenge(userId, dto.getToken(), dto.getChallengeName(), dto.getStartDate(), dto.getEndDate(), dto.getTargetTime(), dto.getTargetDistance(), dto.getSport());
    	
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
    public ResponseEntity<List<Challenge>> challenges(
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
    		@RequestParam(name = "sport",required = false) String sport) {
        List<Challenge> challenges = challengeService.challenges(startDate, endDate, sport);
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
    public ResponseEntity<List<Challenge>> challengeParticipant(
    		@PathVariable("userId") String userId, 
    		@PathVariable("challengeId") String challengeId,
    		@RequestBody String token) {
        challengeService.challengeParticipant(challengeId, userId, token);
        List<Challenge> challenges = challengeService.getAcceptedChallenges(userId, token);
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
