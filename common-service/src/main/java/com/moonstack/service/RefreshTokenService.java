package com.moonstack.service;

import com.moonstack.entity.RefreshToken;
import com.moonstack.entity.User;

import java.util.Optional;

public interface RefreshTokenService
{
    RefreshToken createRefreshToken(String userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
}
