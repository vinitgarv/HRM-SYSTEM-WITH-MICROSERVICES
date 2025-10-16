package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.AuthResponse;
import com.moonstack.response.ApiResponse;
import com.moonstack.service.AuthService;
import com.moonstack.service.UserService;
import com.moonstack.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(authService.login(authRequest,request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken( @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Token refreshed successfully")
                .data(response)
                .build());
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable String  userId) {
        String response = authService.changePassword(changePasswordRequest,userId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(response)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(Message.USER+Message.TAB+Message.REGISTERED+Message.TAB+Message.SUCCESSFULLY+Message.DOT)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/register/super-admin")
    public ResponseEntity<ApiResponse<String>> registerSuperAdmin(@RequestBody RegisterRequest request) {
        authService.registerSuperAdmin(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(Message.USER+Message.TAB+Message.REGISTERED+Message.TAB+Message.SUCCESSFULLY+Message.DOT)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        String token  = jwtTokenUtil.extractToken(request);
        String userId = jwtTokenUtil.extractUserId(token);
        String sessionId = jwtTokenUtil.extractSessionId(token);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .data(authService.logout(userId,sessionId,request))
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword( @RequestBody ForgotPasswordRequest request) {
        String response = authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(response)
                .build());
    }

    @PostMapping("/reset-password/{userId}")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable String  userId, @RequestBody ResetPasswordRequest request) {
        String response = authService.resetPassword(userId,request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(response)
                .build());
    }
}