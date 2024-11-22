package com.example.strava.facade;

import com.example.strava.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    // Endpoint de registro de usuario
    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        // Aquí llamaríamos al servicio de registro de usuario
        return ResponseEntity.ok(userDTO);
    }

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        // Aquí llamaríamos al servicio de autenticación
        return ResponseEntity.ok("token_simulado");
    }

    // Endpoint de logout
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String token) {
        // Aquí implementaríamos la lógica de logout
        return ResponseEntity.ok("Logout successful");
    }
}