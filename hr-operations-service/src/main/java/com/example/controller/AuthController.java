//package com.example.controller;
//
//import com.example.apiResponse.ApiResponse;
//import com.example.constants.Message;
//import com.example.dto.request.AuthRequest;
//import com.example.dto.request.ChangePasswordRequest;
//import com.example.dto.request.RefreshTokenRequest;
//import com.example.dto.response.AuthResponse;
//import com.example.dto.request.RegisterRequest;
//import com.example.service.AuthService;
//import com.example.service.UserService;
//
//import com.example.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtTokenUtil;
//
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest,HttpServletRequest request) {
//        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .multiple(Message.FALSE)
//                .data(authService.login(authRequest,request))
//                .build();
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//    @PostMapping("/refresh-token")
//    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken( @RequestBody RefreshTokenRequest request) {
//        AuthResponse response = authService.refreshToken(request);
//        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("Token refreshed successfully")
//                .data(response)
//                .build());
//    }
//
//    @PostMapping("/changepassword/{userId}")
//    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable String  userId) {
//        String response = authService.changePassword(changePasswordRequest,userId);
//        return ResponseEntity.ok(ApiResponse.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .data(response)
//                .build());
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
//       authService.register(request);
//        ApiResponse<String> response = ApiResponse.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .multiple(Message.FALSE)
//                .data(Message.USER+Message.TAB+Message.REGISTERED+Message.TAB+Message.SUCCESSFULLY+Message.DOT)
//                .build();
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
//        String token  = jwtTokenUtil.extractToken(request);
//        String userId = jwtTokenUtil.extractUserId(token);
//        return ResponseEntity.ok(ApiResponse.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("success")
//                .data(authService.logout(userId,request))
//                .build());
//    }
//}
