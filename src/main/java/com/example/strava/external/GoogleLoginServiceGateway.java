package com.example.strava.external;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.strava.dto.CredentialsDTO;

@Component
public class GoogleLoginServiceGateway implements LoginServiceGateway {

	public Optional<String> externalLogin(CredentialsDTO credentials) {
		return Optional.empty();
	}

}
