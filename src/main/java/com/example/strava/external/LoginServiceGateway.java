package com.example.strava.external;

import java.util.Optional;

import com.example.strava.dto.CredentialsDTO;
import com.example.strava.entity.Credentials;

public interface LoginServiceGateway {
	public Optional<String> externalLogin(Credentials credentials);

}
