package in.hrm.security;

import in.hrm.response.ApiResponse;
import in.hrm.response.UserTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserTokenService {

    private final WebClient webClient;

    public Mono<UserTokenResponse> getUserTokenResponse(String userId,String sessionId) {
        return webClient.get()
                .uri("/user/usertokenresponse/{userId}/{sessionId}", userId,sessionId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserTokenResponse>>() {})
                .map(ApiResponse::getData)
                .doOnNext(data ->log.info(String.format(
                                "Fetched UserTokenResponse for userId %s:\n User Email: %s\n User SessionId: %s\n Access-Token: %s",
                                userId,
                                data.getEmail(),
                                data.getSessionId(),
                                data.getAccessToken())))
                .onErrorResume(e -> {
                    log.error("Failed to fetch UserTokenResponse for userId {}", userId, e);
                    return Mono.empty();
                });
    }
}
