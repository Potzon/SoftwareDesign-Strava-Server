package com.example.strava.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.strava.dto.CredentialsDTO;
import com.example.strava.service.GoogleUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/google")
@Tag(name = "Google Authentication", description = "Handles user login via Google")
public class GoogleUserController {

    @Autowired
    private GoogleUserService googleUserService;

    @Operation(
        summary = "Login with Google account",
        description = "Log in using Google credentials and return a session token.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Login successful"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Email not found or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody CredentialsDTO credentialsDTO) {
        String email = credentialsDTO.getEmail();

        if (!googleUserService.isRegistered(email)) {
            return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.BAD_REQUEST);
        }

        String token = googleUserService.login(email, credentialsDTO.getPassword());

        return ResponseEntity.ok(Map.of("token", token));
    }
}

