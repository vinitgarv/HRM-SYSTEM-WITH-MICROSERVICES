package com.moonstack.service;

import com.moonstack.dtos.request.AuthRequest;
import com.moonstack.dtos.request.ChangePasswordRequest;
import com.moonstack.dtos.request.RefreshTokenRequest;
import com.moonstack.dtos.request.RegisterRequest;
import com.moonstack.dtos.response.AuthResponse;
import com.moonstack.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService
{
    void register(RegisterRequest request);
    AuthResponse login(AuthRequest authRequest, HttpServletRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
   // String logout(String userId,HttpServletRequest request);
    String changePassword(ChangePasswordRequest changePasswordRequest, String userId);

}
