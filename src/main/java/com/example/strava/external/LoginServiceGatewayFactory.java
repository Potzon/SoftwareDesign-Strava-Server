package com.example.strava.external;

public class LoginServiceGatewayFactory {
	public static LoginServiceGateway getLoginService(String externalProvider) {
        switch (externalProvider.toLowerCase()) {
            case "google":
                return new GoogleLoginServiceGateway();
            case "facebook":
                return new FacebookLoginServiceGateway();
            default:
                throw new IllegalArgumentException("Unknown provider: " + externalProvider);
        }
    }
}
