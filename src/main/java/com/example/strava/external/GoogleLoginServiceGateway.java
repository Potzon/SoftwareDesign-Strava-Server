package com.example.strava.external;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class GoogleLoginServiceGateway implements LoginServiceGateway {

	public Optional<String> externalLogin() {
		return Optional.empty();
	}

}
