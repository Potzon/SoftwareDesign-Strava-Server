package com.example.strava.external;

import java.util.Optional;

import com.example.strava.dto.CredentialsDTO;

public abstract interface LoginServiceGateway {
	public Optional<String> externalLogin(CredentialsDTO credentials);

}
