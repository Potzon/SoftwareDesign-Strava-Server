package com.example.strava.service;

import com.example.strava.dto.ChallengeDTO;
import com.example.strava.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.Random;

import java.util.UUID;

@Service
public class UserService {
    public final static Map<String, User> users = new HashMap<>();
    public final static Map<String, User> activeSessions = new HashMap<>();
    static int counter = 0;
    
    
    // Método para registrar un usuario
    public User registerUser(String email, String password, String name, Date birthdate, Integer weight, Integer height,
                                Float maxHeartRate, Float restHeartRate) {
        String userId = String.valueOf(counter++);
        // Crear un nuevo usuario con la lista de desafíos (vacía al inicio)
        User newUser = new User(userId, name, email, password, birthdate, weight, height, maxHeartRate, restHeartRate);
        users.put(userId, newUser);
        return newUser;
    }

    // Método para hacer login
    public String login(String email, String password) {
        // Validar si el email existe en el sistema y la contraseña es correcta
    	User user = users.values().stream()
                            .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                            .findFirst()
                            .orElse(null);
        
        if (user != null) {
            // Si el login es exitoso, se genera un token único
        	String currentToken = generateToken();
            activeSessions.put(currentToken, user); // Guardamos la sesión activa
            return currentToken; // Retornamos el token
        }
        return null; // Login fallido
    }

    // Método para hacer logout
    public String logout(String userId, String token) {
        if (isTokenValid(userId, token)) {
            activeSessions.remove(token);
            return "Logout successful";
        }
        return "Logout failed"; // No hay nadie logueado
    }

    // Método para agregar un desafío a un usuario logueado
    public boolean addChallengeToLoggedUser(ChallengeDTO challenge, String userId,String token) {
    	User user = activeSessions.get(token);
        if (isTokenValid(userId, token)) {
            if (user.getAcceptedChallenges() == null) {
                user.setChallenges(new ArrayList<>()); // Si no tiene desafíos, los inicializamos
            }
            user.addAcceptedChallenge(challenge); // Añadimos el desafío al usuario logueado
            return true;
        }
        return false; // Si no hay usuario logueado
    }

    // Get accepted challenges for a user
    public static Boolean isTokenValid(String userId, String token) {
    	if (UserService.activeSessions.containsKey(token) && UserService.activeSessions.get(token).getUserId().equals(userId)) {
    		return true;
		}else {
			return false;
		}
        
    }
    
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
