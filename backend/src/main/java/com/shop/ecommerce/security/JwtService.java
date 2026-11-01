package com.shop.ecommerce.security;
import io.jsonwebtoken.*;import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;import java.nio.charset.StandardCharsets;import java.util.Date;
import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;
@Service public class JwtService {
 private final SecretKey key;
 public JwtService(@Value("${app.jwt.secret}") String secret){ if(secret==null||secret.length()<32) throw new IllegalArgumentException("Set JWT_SECRET to a random secret of at least 32 characters");key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String issue(String email){return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+86400000L)).signWith(key).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
