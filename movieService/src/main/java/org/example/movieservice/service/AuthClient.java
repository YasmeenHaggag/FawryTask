package org.example.movieservice.service;


import org.example.movieservice.dto.UserInfoDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;


@Service
public class AuthClient {

    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isAdmin(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserInfoDTO> response = restTemplate.exchange(
                    userServiceUrl + "/api/auth/validate",
                    HttpMethod.GET,
                    request,
                    UserInfoDTO.class
            );

            System.out.println(response.getBody().getRole());
            return response.getBody() != null && "ROLE_ADMIN".equals(response.getBody().getRole());
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
