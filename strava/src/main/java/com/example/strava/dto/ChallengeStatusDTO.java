package com.example.strava.dto;

import java.util.Date;

public class ChallengeStatusDTO {
    private String challengeId;
    private String status;
    private Float progress;
    private Date startDate;
    private Date endDate;
    
    // Getters y Setters
	public String getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getProgress() {
		return progress;
	}
	public void setProgress(Float progress) {
		this.progress = progress;
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
	public ChallengeStatusDTO(String challengeId, String status, Float progress, Date startDate, Date endDate) {
		super();
		this.challengeId = challengeId;
		this.status = status;
		this.progress = progress;
		this.startDate = startDate;
		this.endDate = endDate;
	}

   
    
}
