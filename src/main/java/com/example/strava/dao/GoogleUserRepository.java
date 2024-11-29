package com.example.strava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.strava.entity.Credentials;

@Repository
public interface GoogleUserRepository extends JpaRepository<Credentials, String> {

    Credentials findByEmail(String email);
}
