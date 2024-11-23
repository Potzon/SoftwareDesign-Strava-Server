package com.example.strava.facade;


import com.example.strava.entity.User;
import com.example.strava.service.UserService;

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
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	
    @Operation(
    		summary = "Register a new User account",
    		description = "Returns the details of the user account created",
    		responses = {
    			@ApiResponse(responseCode = "200", description = "OK: USer created successfully"),
    			@ApiResponse(responseCode = "500", description = "Internal server error")
    		}
    	)
    @PostMapping("/user")
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
    @PostMapping("/{userId}/logout")
    public ResponseEntity<Map<String, String>> logout(
    		@Parameter(name = "userId", description = "Id of the user", required = true, example = "3")
    		@PathVariable("userId") String userId, 
    		@RequestBody String token) {
    	
    	boolean isLoggedOut = (userService.logout(userId, token).equals("Logout successful"));
        String validation = isLoggedOut ? "Logout successful" : "Logout failed";
        return new ResponseEntity<>(Map.of("validation", validation), HttpStatus.OK);
    }

}
