package com.example.strava.external;

import com.example.strava.entity.Credentials;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class GoogleLoginServiceGateway implements ILoginServiceGateway{

    private static final String HOST = "http://localhost:8900/login";  
    private final RestTemplate restTemplate;

    public GoogleLoginServiceGateway() {
        this.restTemplate = new RestTemplate();
    }

    public Optional<String> externalLogin(Credentials credentials) {
        try {
            // Configurar encabezados y cuerpo de la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Credentials> request = new HttpEntity<>(credentials, headers);

            // Enviar solicitud POST al servicio de Google
            ResponseEntity<String> response = restTemplate.postForEntity(HOST, request, String.class);

            // Verificar la respuesta
            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.of(response.getBody()); // Devolver el token
            } else {
                System.err.println("Login failed with status: " + response.getStatusCode());
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return Optional.empty();
        }
    }
}
