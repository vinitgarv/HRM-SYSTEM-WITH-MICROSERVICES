//package com.moonstack.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class JwtService {
//
//    //@Value("${jwt.secret}")
//    private String jwtSecret;
//
//   // @Value("${jwt.expiration.time}")
//    private long expiration;
//
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("roles", userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList());
//        expiration = 3600000L;
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + expiration);
//        return Jwts.builder()
//                .claims(claims)
//                .subject(userDetails.getUsername())
//                .issuedAt(now)
//                .expiration(expiryDate)
//                .signWith(getSigningKey())
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(getSigningKey())
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            log.error("JWT expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT unsupported: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            log.error("Malformed JWT: {}", e.getMessage());
//        } catch (JwtException e) {
//            log.error("JWT error: {}", e.getMessage());
//        }
//        return false;
//    }
//
//
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .verifyWith((SecretKey) getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
//
//    private SecretKey getSigningKey() {
//        jwtSecret = "asdfghjklqwertyuiopzxcvbnm";
//        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//    }
//
//}
