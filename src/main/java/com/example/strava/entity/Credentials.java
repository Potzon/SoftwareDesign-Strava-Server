package com.example.strava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "credentials")
public class Credentials {
	@Id
	@Column(nullable = false, unique = true)
    private String email;
	
	@Column(nullable = false, unique = false)
	@NotBlank(message="Password cannot be blank")
    private String password;

    public Credentials() {}


    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

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
}

