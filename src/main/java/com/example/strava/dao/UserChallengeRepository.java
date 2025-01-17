package com.example.strava.dao;

import com.example.strava.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, String> {
}
