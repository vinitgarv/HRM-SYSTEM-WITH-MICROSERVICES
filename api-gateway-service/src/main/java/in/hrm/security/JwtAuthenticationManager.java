package in.hrm.security;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
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
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final String secret = "aditya11110009999====dmvknfwvjfvnjksfnvjksdfvdnajfndjkfnj76546546";

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> role.replace("Role : ", "").trim())  // clean role
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // standard Spring Security
                    .collect(Collectors.toList());

            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));

        }
        catch (Exception e) {
            return Mono.empty(); // invalid token triggers 401
        }
    }
}