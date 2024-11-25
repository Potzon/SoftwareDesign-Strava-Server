package com.example.strava.external;

import java.util.Optional;

import com.example.strava.dto.CredentialsDTO;

public class Test {

	
	public class Main {
	    public static void main(String[] args) {
	        // Ejemplo de credenciales para Google
	        CredentialsDTO googleCredentials = new CredentialsDTO();
	        googleCredentials.setEmail("user@gmail.com");
	        googleCredentials.setPassword("password123");
	        googleCredentials.setExternalProvider("f");

	        LoginServiceGateway service = LoginServiceGatewayFactory.getLoginService(googleCredentials);
	        Optional<String> result = service.externalLogin(googleCredentials);
	        result.ifPresent(System.out::println);
	    }
	}
	
}
