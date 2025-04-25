package com.example.Kaushik.My.Day.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET = "T3VyIG1pbmQgaXMgYSBkYW5nZXJvdXMgcGxhY2UuIEJpbmEga3VjaCBzb2NoZSB3byBoYXJyIHVzIHNha3NoIGtvIGJoYWd3YW4gTWFhbiBsZWdhIGppc25lIHVza2Uga2hhYWxpcGFuIG1lIHVzZSAgYXdheiBsYWdhaSBoby4iDQoNCg=="; // At least 32 chars
    private final long EXPIRATION_TIME = 86400000; // 24 hours in ms

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(int userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public int extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
