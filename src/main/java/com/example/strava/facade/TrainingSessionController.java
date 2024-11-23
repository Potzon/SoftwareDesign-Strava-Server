package com.example.strava.facade;


import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.entity.TrainingSession;
import com.example.strava.service.TrainingService;

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
@RequestMapping("/sessions")
public class TrainingSessionController {

    @Autowired
    private TrainingService trainingService;
    
    

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
    public ResponseEntity<TrainingSession> session(
    		@PathVariable("userId") String userId,
    		@RequestBody TrainingSessionDTO dto) {
    	TrainingSession session = trainingService.session(userId, dto.getToken(), dto.getTitle(), dto.getSport(), dto.getDistance(), dto.getStartDate(),dto.getDuration());
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
    public ResponseEntity<List<TrainingSession>> sessions(
    		@PathVariable("userId") String userId,
    		@RequestBody String token,
    		@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@RequestParam(name = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<TrainingSession> sessions = trainingService.sessions(token, startDate, endDate);
        if (sessions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
   
}
