package com.example.strava.dto;

public class CredentialsDTO {
	private String email;
	private String password;
	private String externalProvider;
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
	public String getExternalProvider() {
		return externalProvider;
	}

	public void setExternalProvider(String externalProvider) {
		this.externalProvider = externalProvider;
	}
	public CredentialsDTO() {
		
	}

	public CredentialsDTO(String email, String password, String externalProvider) {
		super();
		this.email = email;
		this.password = password;
		this.externalProvider = externalProvider;
	}



}
