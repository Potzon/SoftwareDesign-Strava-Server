package com.example.strava.service;

import com.example.strava.dto.ChallengeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengeService {

    private final List<ChallengeDTO> challenges = new ArrayList<>();

    public ChallengeDTO addChallenge(ChallengeDTO challengeDTO) {
        challenges.add(challengeDTO);
        return challengeDTO;
    }

    public List<ChallengeDTO> getAllChallenges() {
        return challenges;
    }
}
