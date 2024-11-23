package com.example.strava.dto;

import java.util.Date;

//import jakarta.persistence.Entity;
//
//@Entity
public class TrainingSessionDTO {
	private String token;
    private String title;
    private String sport;
    private Float distance;
    private Date startDate;
    private Float duration;
    
    
    public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public Float getDuration() {
		return duration;
	}
	public void setDuration(Float duration) {
		this.duration = duration;
	}
	public TrainingSessionDTO() {
		
	}
	
	public TrainingSessionDTO(String token, String title, String sport, Float distance, Date startDate,
			Float duration) {
		super();
		this.token = token;
		this.title = title;
		this.sport = sport;
		this.distance = distance;
		this.startDate = startDate;
		this.duration = duration;
	}

    
}
