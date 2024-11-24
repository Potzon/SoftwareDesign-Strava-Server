package com.example.strava.dto;

public class CredentialsDTO {
	private String email;
	private String password;

	// Getters y Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public CredentialsDTO() {
		
	}

	public CredentialsDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	

}
