package com.example.strava.service;

import com.example.strava.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDTO registerUser(UserDTO userDTO) {
        // Aquí implementaríamos la lógica de registro, p.ej., guardando el usuario en la BD.
        return userDTO;
    }

    public String login(String email, String password) {
        // Lógica de autenticación (por ejemplo, verificar en la base de datos)
        return "token_simulado";
    }

    public String logout(String token) {
        // Lógica para invalidar el token
        return "Logout successful";
    }
}
