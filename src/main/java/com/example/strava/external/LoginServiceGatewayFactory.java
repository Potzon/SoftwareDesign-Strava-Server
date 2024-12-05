package com.example.strava.external;

public class LoginServiceGatewayFactory {
	public static ILoginServiceGateway createLoginService(String externalProvider) {
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
