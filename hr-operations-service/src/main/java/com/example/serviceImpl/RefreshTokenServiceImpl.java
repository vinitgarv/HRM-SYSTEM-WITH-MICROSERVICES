//package com.example.serviceImpl;
//
//import com.example.entity.RefreshToken;
//import com.example.entity.User;
//import com.example.repository.RefreshTokenRepository;
//import com.example.repository.UserRepository;
//import com.example.service.RefreshTokenService;
//import com.example.util.UtilsMethods;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class RefreshTokenServiceImpl implements RefreshTokenService {
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public RefreshToken createRefreshToken(String userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        RefreshToken refreshToken = RefreshToken.builder()
//                .user(user)
//                .isActive(true)
//                .isDeleted(false)
//                .id(UtilsMethods.generateId())
//                .token(UUID.randomUUID().toString())
//                .expiryDate(LocalDateTime.now().plusMinutes(7 * 24 * 60 * 60))
//                .build();
//
//        Optional<RefreshToken> oldToken = refreshTokenRepository.findByUser(user);
//        oldToken.ifPresent(token -> {
//                    refreshToken.setId(token.getId());
//                    refreshToken.setCreatedAt(token.getCreatedAt());
//                    refreshToken.setUpdatedAt(LocalDateTime.now());
//                });
//        return refreshTokenRepository.save(refreshToken);
//    }
//
//    @Override
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    @Override
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
//            refreshTokenRepository.delete(token);
//            throw new RuntimeException("Refresh token expired. Please login again.");
//        }
//        return token;
//    }
//
////    @Override
////    public int deleteByUserId(String userId) {
////        User user = userRepository.findById(userId).orElseThrow();
////        return refreshTokenRepository.deleteByUser(user);
////    }
//}
