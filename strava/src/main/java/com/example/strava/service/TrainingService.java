package com.example.strava.service;

import com.example.strava.dto.TrainingSessionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {

    private final List<TrainingSessionDTO> trainingSessions = new ArrayList<>();

    public TrainingSessionDTO addSession(TrainingSessionDTO sessionDTO) {
        trainingSessions.add(sessionDTO);
        return sessionDTO;
    }

    public List<TrainingSessionDTO> getAllSessions() {
        return trainingSessions;
    }
}
