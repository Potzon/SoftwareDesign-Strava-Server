package com.example.strava.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.strava.dto.ChallengeDTO;
import com.example.strava.dto.TrainingSessionDTO;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Date birthdate;
    private Integer weight;
    private Integer height;
    private Float maxHeartRate;
    private Float restHeartRate;
    private ArrayList<ChallengeDTO> acceptedChallenges;
    private ArrayList<TrainingSessionDTO> trainingSessions;
    
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
	public void setAcceptedChallenges(ArrayList<ChallengeDTO> acceptedChallenges) {
		this.acceptedChallenges = acceptedChallenges;
	}
	public ArrayList<ChallengeDTO> getAcceptedChallenges() {
		return acceptedChallenges;
	}
	public void setChallenges(ArrayList<ChallengeDTO> acceptedChallenges) {
		this.acceptedChallenges = acceptedChallenges;
	}
	
	public void addAcceptedChallenge(ChallengeDTO challenge) {
		this.acceptedChallenges.add(challenge);
	}
	public void addSessionToUser(TrainingSessionDTO session) {
		this.trainingSessions.add(session);
	}
	
	public List<TrainingSessionDTO> getTrainingSessions() {
		return trainingSessions;
	}
	public void setTrainingSessions(ArrayList<TrainingSessionDTO> trainingSessions) {
		this.trainingSessions = trainingSessions;
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
		this.acceptedChallenges = new ArrayList<ChallengeDTO>();
		this.trainingSessions = new ArrayList<TrainingSessionDTO>();
	}
}

