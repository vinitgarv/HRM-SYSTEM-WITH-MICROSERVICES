package in.hrm.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.hrm.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        String path = exchange.getRequest().getPath().value();
        String json = String.format(
                "{\"status\":401,\"message\":\"Unauthorized access. Please login.\",\"path\":\"%s\"}", path
        );
        ApiResponse<Object> apiResponse = ApiResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Unauthorized access. Please login.")
                .data(null)
                .multiple(false)
                .build();
        var response = exchange.getResponse();
        String apiResponseJson = "";
        try {
            apiResponseJson = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException ex) {
            // fallback to hardcoded response if serialization fails
            apiResponseJson = json;
        }

        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = apiResponseJson.getBytes(StandardCharsets.UTF_8);
        var buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }
}
