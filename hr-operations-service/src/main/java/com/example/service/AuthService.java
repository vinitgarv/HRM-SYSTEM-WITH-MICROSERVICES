//package com.example.service;
//
//import com.example.dto.request.AuthRequest;
//import com.example.dto.request.ChangePasswordRequest;
//import com.example.dto.request.RefreshTokenRequest;
//import com.example.dto.request.RegisterRequest;
//import com.example.dto.response.AuthResponse;
//import jakarta.servlet.http.HttpServletRequest;
//
//public interface AuthService {
//    void register(RegisterRequest request);
//    AuthResponse login(AuthRequest authRequest, HttpServletRequest request);
//    AuthResponse refreshToken(RefreshTokenRequest request);
//    String logout(String userId,HttpServletRequest request);
//    String changePassword(ChangePasswordRequest changePasswordRequest, String userId);
//}
