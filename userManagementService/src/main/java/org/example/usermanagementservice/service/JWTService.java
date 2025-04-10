package org.example.usermanagementservice.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.usermanagementservice.exceptions.OtpOrTokenExpiredException;
import org.example.usermanagementservice.exceptions.TokenExpiredException;
import org.example.usermanagementservice.models.Users;
import org.example.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final Key key;
    private final UserRepository userRepository;

    public JWTService(@Value("${jwt.secret.key}") String secretKey, UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.userRepository = userRepository;
    }


    public String generateToken(String username, String role , long expiration) {
        Optional<Users> user = userRepository.findByUsername(username);
        return Jwts.builder()
                .setSubject(username)
                .claim("id",user.get().getId())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        return extractClaims(token).getSubject();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public String extractTokenFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization").substring(7);
    }
    public long extractID(HttpServletRequest request) {
        return extractClaims(extractTokenFromHeader(request)).get("id",Long.class);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenValid(HttpServletRequest request, String username) {
        try {
            String token = extractTokenFromHeader(request);
            if (token == null) {
                throw new IllegalArgumentException("Header with no token.");
            }
            String tokenUsername = extractUsername(request);
            if (!username.equals(tokenUsername)) {
                throw new IllegalArgumentException("No matching between username and token username");
            }
            if (isTokenExpired(token)) {
                throw new OtpOrTokenExpiredException("Expired Token");
            }
            return true;

        }catch(OtpOrTokenExpiredException exception){
                throw exception;
            } catch(IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid token: " + e.getMessage());
            } catch(Exception e){
                throw new RuntimeException("An unexpected error occurred during token validation.", e);
            }
    }


    private boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new TokenExpiredException("Expired Token.");
        }

        return false;
        //return extractClaims(token).getExpiration().before(new Date());
    }
}
