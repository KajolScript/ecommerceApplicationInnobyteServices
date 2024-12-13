package com.internship.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_here"; // Replace with your secure secret key
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // Generate a JWT token
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    // Check if the token is expired
    public static boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Validate a token
    public static boolean validateToken(String token, String userId) {
        String extractedUserId = extractUserId(token);
        return extractedUserId.equals(userId) && !isTokenExpired(token);
    }

    // Get all claims from the token
    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    
    public static String extractUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("your-secret-key") // Replace with your JWT secret
                    .parseClaimsJws(token)
                    .getBody();
            // Assuming "userId" is the claim that contains the user ID
            return claims.get("userId", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
    }

}
