package com.example.strava.external;

import java.util.Optional;

import com.example.strava.dto.CredentialsDTO;

public class LoginServiceGatewayFactory {
	public static LoginServiceGateway getLoginService(CredentialsDTO credentials) {
        switch (credentials.getExternalProvider().toLowerCase()) {
            case "google":
                return new GoogleLoginServiceGateway();
            case "facebook":
                return new FacebookLoginServiceGateway();
            default:
                throw new IllegalArgumentException("Unknown provider: " + credentials.getExternalProvider());
        }
    }
}
