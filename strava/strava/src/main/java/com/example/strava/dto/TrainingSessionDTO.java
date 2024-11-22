package com.example.strava.dto;

import java.util.Date;

public class TrainingSessionDTO {
    private String sessionId;
    private String title;
    private String sport;
    private Float distance;
    private Date startDate;
    private String duration;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public TrainingSessionDTO(String sessionId, String title, String sport, Float distance, Date startDate,
			String duration) {
		super();
		this.sessionId = sessionId;
		this.title = title;
		this.sport = sport;
		this.distance = distance;
		this.startDate = startDate;
		this.duration = duration;
	}

    
}
