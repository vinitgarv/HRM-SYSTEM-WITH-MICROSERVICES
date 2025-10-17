package in.hrm.security;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager authenticationManager;

    public JwtSecurityContextRepository(JwtAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("\n*\n*\n "+exchange.getRequest().toString()+"\n*\n*\n ");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        log.info("\n*\n*\n "+exchange.getRequest().getHeaders()+"\n*\n*\n ");

        log.info("\n*\n*\n "+authHeader+"\n*\n*\n ");

        log.info("\n*\n*\n OUTSIDE \n*\n*\n");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            log.info("\n*\n*\n INSIDE \n*\n*\n");
            String authToken = authHeader.substring(7);
            Authentication auth = new UsernamePasswordAuthenticationToken(null, authToken);
            return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
        }

        return Mono.empty();
    }

//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange exchange) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            // Throw 401 Unauthorized if token is missing or malformed
//            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization token is missing or invalid"));
//        }
//
//        String authToken = authHeader.substring(7);
//        Authentication auth = new UsernamePasswordAuthenticationToken(null, authToken);
//
//        return authenticationManager.authenticate(auth)
//                .map(SecurityContextImpl::new);
//    }


    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty(); // Not needed
    }
}
