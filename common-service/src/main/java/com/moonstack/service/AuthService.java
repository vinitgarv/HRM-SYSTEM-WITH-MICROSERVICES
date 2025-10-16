package com.moonstack.service;

import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.AuthResponse;
import com.moonstack.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService
{
    void register(RegisterRequest request);

    void registerSuperAdmin(RegisterRequest request);

    AuthResponse login(AuthRequest authRequest, HttpServletRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    String logout(String userId,String sessionId,HttpServletRequest request);
    String changePassword(ChangePasswordRequest changePasswordRequest, String userId);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(String userId, ResetPasswordRequest request);
}
