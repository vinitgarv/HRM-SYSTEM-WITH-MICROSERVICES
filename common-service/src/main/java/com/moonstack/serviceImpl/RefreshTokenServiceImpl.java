package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.entity.RefreshToken;
import com.moonstack.entity.User;
import com.moonstack.exception.NotFoundException;
import com.moonstack.repository.RefreshTokenRepository;
import com.moonstack.repository.UserRepository;
import com.moonstack.service.RefreshTokenService;
import com.moonstack.service.UserService;
import com.moonstack.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService
{
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .isActive(true)
                .deleted(false)
                .id(Helper.generateId())
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusMinutes(7 * 24 * 60 * 60))
                .build();

        Optional<RefreshToken> oldToken = refreshTokenRepository.findByUser(user);
        oldToken.ifPresent(token -> {
            refreshToken.setId(token.getId());
            refreshToken.setCreatedAt(token.getCreatedAt());
            refreshToken.setUpdatedAt(LocalDateTime.now());
        });
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

}
