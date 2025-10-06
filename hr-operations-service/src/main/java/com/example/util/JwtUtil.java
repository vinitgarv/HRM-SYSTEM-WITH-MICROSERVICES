//package com.example.util;
//
//import com.example.entity.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.Set;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//    private static final String secretKey = "aditya11110009999====dmvknfwvjfvnjksfnvjksdfvdnajfndjkfnj76546546";
//
//    // Generate JWT token with HS512 algorithm
//    public String generateJwtToken(String username, String userId, int tokenVersion, Set<String> roles) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("userId", userId)
//                .claim("roles", roles.stream()
//                        .map(role -> role.replace("Role : ", "").trim()) // clean roles
//                        .toList())
//                .claim("tokenVersion", tokenVersion)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//                .signWith(getKey(), SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    private SecretKey getKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    // Extract all claims safely
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public String extractUserId(String token) {
//        return extractClaim(token, claims -> claims.get("userId", String.class));
//    }
//
//    public int extractTokenVersion(String token) {
//        return extractClaim(token, claims -> claims.get("tokenVersion", Integer.class));
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, User user) {
//        return extractUsername(token).equals(user.getEmail()) &&
//                !isTokenExpired(token) &&
//                extractTokenVersion(token) == user.getTokenVersion();
//    }
//
//    public String extractToken(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null; // safer than returning "Token Invalid"
//    }
//}