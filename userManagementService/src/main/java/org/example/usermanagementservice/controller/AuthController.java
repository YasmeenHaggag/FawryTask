package org.example.usermanagementservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.usermanagementservice.dto.LoginRequest;
import org.example.usermanagementservice.dto.RegistrationRequest;
import org.example.usermanagementservice.dto.UserInfoDTO;
import org.example.usermanagementservice.service.AuthService;
import org.example.usermanagementservice.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody LoginRequest loginRequest)
    {
        return authService.login(loginRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/refresh-token")
    public Map<String,String> refreshToken(HttpServletRequest request,
                                           HttpServletResponse response)
    {
        return  authService.refreshToken(request,response);
    }



    @GetMapping("/validate")
    public ResponseEntity<UserInfoDTO> validateToken(@RequestHeader("Authorization")
                                                         String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtService.extractUsername(token);
        String role = jwtService.extractRole(token);
        System.out.println(token);
        System.out.println(role);
        return ResponseEntity.ok(new UserInfoDTO(username, role));
    }

    @GetMapping("/extract-role")
    public String extractRoleFromToken(@RequestParam String token){
        return jwtService.extractRole(token);
    }


}
