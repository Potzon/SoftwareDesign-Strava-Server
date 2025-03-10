package com.example.strava.service;

import com.example.strava.dao.UserChallengeRepository;
import com.example.strava.dao.UserRepository;
import com.example.strava.dto.CredentialsDTO;
import com.example.strava.entity.Challenge;
import com.example.strava.entity.Credentials;
import com.example.strava.entity.User;
import com.example.strava.entity.UserChallenge;
import com.example.strava.external.LoginServiceGatewayFactory;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
	final private UserRepository userRepository;
	final private UserChallengeRepository userChallengeRepository;
	
    public final static Map<String, User> activeSessions = new HashMap<>();
    
	public UserService(UserRepository userRepository, UserChallengeRepository userChallengeRepository) {
		this.userRepository = userRepository;
		this.userChallengeRepository = userChallengeRepository;
	}
    
    
    // Método para registrar un usuario
    public User registerUser(String email, String password, String name, Date birthdate, Integer weight, Integer height,
                                Float maxHeartRate, Float restHeartRate) {
        String userId = generateToken();
        User newUser = new User(userId, name, email, password, birthdate, weight, height, maxHeartRate, restHeartRate);
        userRepository.save(newUser);
        return newUser; 
    }

    // Método para hacer login
    public String login(CredentialsDTO dto) {
        // Validar si el email existe en el sistema y la contraseña es correcta
    	Optional<User> user = userRepository.findByEmail(dto.getEmail());

        if (user.get().checkEmail(dto.getEmail()) && user.get().checkPassword(dto.getPassword())) {
            Optional<String> tokenOpt = LoginServiceGatewayFactory.createLoginService(dto.getExternalProvider()).externalLogin(new Credentials(dto.getEmail(), dto.getPassword())); 
            if (tokenOpt.isEmpty()) {
                return null;
            }
            String token = tokenOpt.get();
            activeSessions.put(token, user.get());  
			for (String i : activeSessions.keySet()) {
				System.out.println("Token "+ i + " User "+ activeSessions.get(i).getUserId());
				
			}
            return token;
        } else {
            return null;
        }
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
    public boolean addChallengeToLoggedUser(Challenge challenge, String userId,String token) {
    	User user = activeSessions.get(token);
        if (isTokenValid(userId, token)) {
            if (user.getAcceptedChallenges() == null) {
                user.setChallenges(new ArrayList<>()); // Si no tiene desafíos, los inicializamos
            }
            UserChallenge userChallenge = new UserChallenge(user, challenge, 0);
            user.addAcceptedChallenge(userChallenge); 
            userRepository.save(user);
            userChallengeRepository.save(userChallenge);
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
    public static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
