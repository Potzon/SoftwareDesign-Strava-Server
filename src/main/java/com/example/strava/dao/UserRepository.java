package com.example.strava.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.strava.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	 Optional<User> findByUserId(String userId);
	 Optional<User> findByEmail(String email);
}
