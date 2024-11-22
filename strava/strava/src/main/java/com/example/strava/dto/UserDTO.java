package com.example.strava.dto;

public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private Integer weight;
    private Integer height;
    private Float maxHeartRate;
    private Float restHeartRate;
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
	public UserDTO(String userId, String name, String email, Integer weight, Integer height, Float maxHeartRate,
			Float restHeartRate) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.weight = weight;
		this.height = height;
		this.maxHeartRate = maxHeartRate;
		this.restHeartRate = restHeartRate;
	}

    
}
