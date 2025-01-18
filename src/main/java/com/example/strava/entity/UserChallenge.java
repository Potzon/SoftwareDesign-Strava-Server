package com.example.strava.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
@Entity
@Table(name = "user_challenge", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "challenge_id"}))
public class UserChallenge {

	@Id
	@Column(nullable = false, unique = true)
    private String id; 

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id", referencedColumnName = "challengeId")
    private Challenge challenge;

    @Column(nullable = false)
    private Integer progress; 

    public UserChallenge() {}

    public UserChallenge(User user, Challenge challenge, Integer progress) {
    	this.id = UserChallenge.generateToken();
        this.user = user;
        this.challenge = challenge;
        this.progress = progress;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    

    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
