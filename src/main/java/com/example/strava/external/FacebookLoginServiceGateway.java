package com.example.strava.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

import com.example.strava.dto.CredentialsDTO;


public class FacebookLoginServiceGateway implements LoginServiceGateway {
	private static final String HOST = "localhost"; // Server host
    private static final int PORT = 8080;           // Server port
	
	//credentialsdto object with email str and pass str
	public Optional<String> externalLogin(CredentialsDTO credentials){
		
		try (Socket socket = new Socket(HOST, PORT);
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

	            // Send email and password to server
	            out.println(credentials.getEmail());
	            out.println(credentials.getPassword());

	            // Read server response
	            String response = in.readLine();
	            if (response.startsWith("SUCCESS:")) {
	                return Optional.of(response.split(":")[1]); // Return token
	            } else {
	                System.err.println("Login failed: " + response);
	                return Optional.empty();
	            }

	        } catch (IOException e) {
	            System.err.println("Client error: " + e.getMessage());
	            return Optional.empty();
	        }
	    }
		
	

}
