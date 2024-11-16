package com.polstat.perpustakaan.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // Generate the access token for a user with a single role
    public String generateAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);

        // Ensure the role has "ROLE_" prefix only once
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.trim();  // Add prefix only if it is missing
        }
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    // Retrieve claims from the token
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract the email from the token
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Retrieve the role from the token, ensuring it has the "ROLE_" prefix
    public String getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        String role = (String) claims.get("role");

        // Ensure role is prefixed with "ROLE_"
        return role != null && role.startsWith("ROLE_") ? role : "ROLE_" + role;
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Validate if the token is still valid
    public boolean isValidToken(String token) {
        return !isTokenExpired(token);
    }
}
