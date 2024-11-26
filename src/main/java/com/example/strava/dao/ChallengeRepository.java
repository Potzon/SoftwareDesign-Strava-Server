package com.example.strava.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.strava.dto.ChallengeDTO;
import com.example.strava.entity.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, String>{
	Optional<Challenge> findById(String challengeId);
	List<Challenge> findByUserId(String userId);
	List<Challenge> findByStartDateBetween(Date startDate, Date endDate);
	List<Challenge> findBySport(String sport);
}
