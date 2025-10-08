package com.moonstack.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
}
