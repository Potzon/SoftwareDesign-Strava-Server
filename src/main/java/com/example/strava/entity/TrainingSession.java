package com.example.strava.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class TrainingSession {
	
	@Id
	@Column(nullable = false, unique = true)
    private String sessionId;
	
	 @ManyToOne
	 @JoinColumn(name = "user_id")
	 private User user;

	@Column(nullable = false, unique = false)
    private String title;
    
    @Column(nullable = false, unique = false)
    private String sport;
    
    @Column(nullable = false, unique = false)
    private Float distance;
    
    @Column(nullable = false, unique = true)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(nullable = false, unique = false)
    private Float duration;
    
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
	public Float getDuration() {
		return duration;
	}
	public void setDuration(Float duration) {
		this.duration = duration;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public TrainingSession() {
		
	}
	
	public TrainingSession(String sessionId, User user, String title, String sport, Float distance, Date startDate,
			Float duration) {
		super();
		this.sessionId = sessionId;
		this.user = user;
		this.title = title;
		this.sport = sport;
		this.distance = distance;
		this.startDate = startDate;
		this.duration = duration;
	}

    
}
