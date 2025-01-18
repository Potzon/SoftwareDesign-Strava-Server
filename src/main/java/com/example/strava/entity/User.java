package com.example.strava.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    @Column(nullable = true)
    private Integer weight;
    
    @Column(nullable = true)
    private Integer height;
    
    @Column(nullable = true)
    private Float maxHeartRate;
    
    @Column(nullable = true)
    private Float restHeartRate;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserChallenge> acceptedChallenges = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TrainingSession> trainingSessions;
    
    
    
    
    
    //Getters setters
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Float getMaxHeartRate() {
		return maxHeartRate;
	}
	public void setMaxHeartRate(Float maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}
	public Float getRestHeartRate() {
		return restHeartRate;
	}
	public void setRestHeartRate(Float restHeartRate) {
		this.restHeartRate = restHeartRate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public void setAcceptedChallenges(ArrayList<UserChallenge> acceptedChallenges) {
		this.acceptedChallenges = acceptedChallenges;
	}
	public List<UserChallenge> getAcceptedChallenges() {
		return acceptedChallenges;
	}
	public void setChallenges(ArrayList<UserChallenge> acceptedChallenges) {
		this.acceptedChallenges = acceptedChallenges;
	}
	
	public void addAcceptedChallenge(UserChallenge challenge) {
		this.acceptedChallenges.add(challenge);
	}
	public void addSessionToUser(TrainingSession session) {
		this.trainingSessions.add(session);
	}
	
	public List<TrainingSession> getTrainingSessions() {
		return trainingSessions;
	}
	public void setTrainingSessions(ArrayList<TrainingSession> trainingSessions) {
		this.trainingSessions = trainingSessions;
	}
	
	public boolean checkPassword(String password) {
        return this.password.equals(password);
	}
	
	public boolean checkEmail(String email) {
        return this.email.equals(email);
	}
	
    public User() {
    }
	
	public User(String userId, String name, String email, String password, Date birthdate, Integer weight,
			Integer height, Float maxHeartRate, Float restHeartRate) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.birthdate = birthdate;
		this.weight = weight;
		this.height = height;
		this.maxHeartRate = maxHeartRate;
		this.restHeartRate = restHeartRate;
		this.acceptedChallenges = new ArrayList<UserChallenge>();
		this.trainingSessions = new ArrayList<TrainingSession>();
	}
	
	@JsonIgnore
	public ArrayList<Challenge> getChallenges() {
		ArrayList<Challenge> challenges = new ArrayList<>();
	    
	    for (UserChallenge userChallenge : this.acceptedChallenges) {
	        challenges.add(userChallenge.getChallenge());
	    }
	    return challenges;
	}
	@JsonIgnore
	public List<UserChallenge> getUserChallenges(){
		return this.acceptedChallenges;
	}
}

