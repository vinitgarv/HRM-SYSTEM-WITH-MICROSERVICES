package com.example.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetAllotmentResponse
{
    private String id;
    private AssetResponse assetResponse;
    private UserResponse userResponse;
}
