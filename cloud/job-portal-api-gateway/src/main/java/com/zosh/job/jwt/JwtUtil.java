package com.zosh.job.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).get("email", String.class);
  }

  public String extractAuthorities(String token) {
    return extractAllClaims(token).get("authorities", String.class);
  }

  public Long extractUserId(String token) {
    return extractAllClaims(token).get("userId", Long.class);
  }

  public boolean isTokenValid(String token) {
    try {
      // Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
      // return true;
      extractAllClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
