package com.example.strava.dto;

import java.util.Date;

public class ChallengeDTO {
    private String token;
    private String challengeName;
    private Date startDate;
    private Date endDate;
    private Integer targetTime;
    private Float targetDistance;
    private String sport;
    

    // Getters y Setters
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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

	public ChallengeDTO() {
	}
	
	public ChallengeDTO(String token, String challengeName, Date startDate, Date endDate,
			Integer targetTime, Float targetDistance, String sport) {
		super();
		this.token = token;
		this.challengeName = challengeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.targetTime = targetTime;
		this.targetDistance = targetDistance;
		this.sport = sport;
	}


    
}
