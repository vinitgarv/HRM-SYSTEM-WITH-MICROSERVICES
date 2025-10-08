package in.hrm.security;

import in.hrm.response.ApiResponse;
import in.hrm.response.UserTokenResponse;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
//
//@Component
//public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
//
//    private final String secret = "metalibabcdefghijklmnopqrstuvwxyz"; // Use env var in real app
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        String authToken = authentication.getCredentials().toString();
//
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
//                    .build()
//                    .parseClaimsJws(authToken)
//                    .getBody();
//
//            String username = claims.getSubject();
//            List<String> roles = claims.get("roles", List.class);
//
//            List<GrantedAuthority> authorities = roles.stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                    .collect(Collectors.toList());
//
//            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
//
//        } catch (JwtException e) {
//            return Mono.empty(); // Invalid token
//        }
//    }
//
//}

@Component
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final String secret = "aditya11110009999====dmvknfwvjfvnjksfnvjksdfvdnajfndjkfnj76546546";

    @Autowired
    private UserTokenService userTokenService;



    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        try {
            // Parse JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();

            String username = claims.getSubject();
            String userId = claims.get("userId", String.class);
            Integer tokenVersionFromJwt = claims.get("tokenVersion", Integer.class);

            log.info("Authenticating user: {}, tokenVersion: {}", username, tokenVersionFromJwt);

            // Fetch UserTokenResponse from COMMON-SERVICE
            return userTokenService.getUserTokenResponse(userId)
                    .flatMap(tokenResponse -> validateTokenVersion(tokenResponse, tokenVersionFromJwt, username, claims))
                    .switchIfEmpty(Mono.empty());

        } catch (Exception e) {
            log.error("Invalid JWT token", e);
            return Mono.empty();
        }
    }

    private Mono<Authentication> validateTokenVersion(UserTokenResponse tokenResponse, Integer tokenVersionFromJwt,
                                                      String username, Claims claims) {
        if (!tokenResponse.getTokenVersion().equals(tokenVersionFromJwt)) {
            log.warn("Token version mismatch for user {}", username);
            return Mono.empty();
        }

        List<String> roles = claims.get("roles", List.class);
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
    }
}