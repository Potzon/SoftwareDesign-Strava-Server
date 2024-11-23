package com.example.strava.entity;

import java.util.Date;

//import jakarta.persistence.Entity;
//
//@Entity
public class Challenge {
    private String userId;
    private String challengeId;
    private String challengeName;
    private Date startDate;
    private Date endDate;
    private Integer targetTime;
    private Float targetDistance;
    private String sport;
    

    // Getters y Setters
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getTargetTime() {
		return targetTime;
	}
	public void setTargetTime(Integer targetTime) {
		this.targetTime = targetTime;
	}
	public Float getTargetDistance() {
		return targetDistance;
	}
	public void setTargetDistance(Float targetDistance) {
		this.targetDistance = targetDistance;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getChallengeName() {
		return challengeName;
	}
	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public Challenge() {
	}
	
	public Challenge(String userId, String challengeId, String challengeName, Date startDate, Date endDate,
			Integer targetTime, Float targetDistance, String sport) {
		super();
		this.userId = userId;
		this.challengeId = challengeId;
		this.challengeName = challengeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.targetTime = targetTime;
		this.targetDistance = targetDistance;
		this.sport = sport;
	}


    
}
