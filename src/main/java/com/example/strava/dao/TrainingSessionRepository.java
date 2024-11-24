package com.example.strava.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.strava.dto.TrainingSessionDTO;
import com.example.strava.entity.TrainingSession;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, String>{
	 List<TrainingSession> findByStartDateBetween(Date startDate, Date endDate);

}
