package in.hrm.handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.hrm.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        String path = exchange.getRequest().getPath().value();
        String json = String.format(
                "{\"status\":403,\"message\":\"Access denied. You do not have permission to access this resource.\",\"path\":\"%s\"}", path
        );
        ApiResponse<Object> apiResponse = ApiResponse.builder().statusCode(HttpStatus.FORBIDDEN.value())
                .message("Access denied. You do not have permission to access this resource.")
                .data(null)
                .multiple(false)
                .build();
        var response = exchange.getResponse();
        String apiResponseJson = "";
        try {
            apiResponseJson= objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            // fallback to hardcoded response if serialization fails
            apiResponseJson = json;
        }
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var buffer = response.bufferFactory().wrap(apiResponseJson.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}
