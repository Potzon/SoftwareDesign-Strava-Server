package com.example.strava.external;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.strava.dto.CredentialsDTO;

@Component
public class GoogleLoginServiceGateway implements LoginServiceGateway {

	public Optional<String> externalLogin(CredentialsDTO credentials) {
/*
		@Component
		public class GoogleLoginServiceGateway implements LoginServiceGateway {

		    @Autowired
		    private GoogleUserService googleUserService;

		    @Override
		    public Optional<String> externalLogin(CredentialsDTO credentials) {
		        try {
		            String token = googleUserService.login(credentials.getEmail(), credentials.getPassword());
		            return Optional.of(token);
		        } catch (RuntimeException e) {
		        
		            System.err.println("Login failed: " + e.getMessage());
		            */
		            return Optional.empty();
		            /*
		        }
		    }
		}

	}

*/
	}
	
}