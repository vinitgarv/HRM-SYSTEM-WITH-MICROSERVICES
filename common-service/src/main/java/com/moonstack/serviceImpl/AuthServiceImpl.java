package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.AuthResponse;
import com.moonstack.entity.RefreshToken;
import com.moonstack.entity.Role;
import com.moonstack.entity.User;
import com.moonstack.exception.AlreadyPresentException;
import com.moonstack.exception.NotFoundException;
import com.moonstack.repository.RefreshTokenRepository;
import com.moonstack.repository.UserRepository;
import com.moonstack.security.CustomUserDetailsService;
import com.moonstack.service.AuthService;
import com.moonstack.service.RefreshTokenService;
import com.moonstack.service.SessionLogsService;
import com.moonstack.service.UserService;
import com.moonstack.utils.Helper;
import com.moonstack.utils.IpUtils;
import com.moonstack.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private SessionLogsService sessionLogsService;

    @Override
    public void register(RegisterRequest request)
    {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new AlreadyPresentException(Message.EMAIL+Message.TAB+Message.ALREADY+Message.TAB+Message.PRESENT+Message.DOT);
        }

        User user = new User();
        user.setId(Helper.generateId());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTempPassword(Helper.generateRandomPassword());
        user.setAccType("Initiated");

        if (request.getRoles() != null)
        {
            for (String roleName : request.getRoles())
            {
                Role r = new Role();
                r.setId(Helper.generateId());
                r.setName(roleName);
                user.addRole(r);
            }
        }
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest, HttpServletRequest request) {
        User user = null;
        String accessToken = null;
        String refreshTokenValue = null;
        String reason = null;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setTokenVersion(user.getTokenVersion() + 1);
            userRepository.save(user);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            accessToken = jwtTokenUtil.generateJwtToken(userDetails.getUsername(), user.getId(),user.getTokenVersion(), roles);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            refreshTokenValue = refreshToken.getToken();

            return AuthResponse.builder()
                    .token(accessToken)
                    .refreshToken(refreshTokenValue)
                    .build();

        } catch (Exception ex) {
            reason = ex.getMessage();
            user = userRepository.findByEmail(authRequest.getEmail()).orElse(null);

            throw ex;
        } finally {
            final User finalUser = user;
            String clientIp = IpUtils.getClientIp(request);
            SessionLogsRequest logRequest = SessionLogsRequest.builder()
                    .action(Message.LOGIN)
                    .reason(reason)
                    .user(user)
                    .ipAddress(clientIp)
                    .build();
            if (finalUser != null) {
                new Thread(() -> {
                    try {
                        sessionLogsService.recordLogin(logRequest);
                    } catch (Exception logEx) {
                        logEx.printStackTrace();
                    }
                }).start();
            }
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        User user = refreshToken.getUser();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String newAccessToken = jwtTokenUtil.generateJwtToken(userDetails.getUsername(),user.getId(),user.getTokenVersion(),roles);

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(requestRefreshToken)
                .build();
    }

    @Override
    public String logout(String userId,HttpServletRequest request) {
        String reason =null;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        try {
            RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                    .orElseThrow(() -> new NotFoundException("Invalid Refresh Token"));
            refreshToken.setToken(null);
            refreshToken.setExpiryDate(null);
            refreshToken.setUpdatedAt(LocalDateTime.now());
            refreshTokenRepository.save(refreshToken);
        } catch (Exception ex) {
            reason = ex.getMessage();
        }

        String clientIP = IpUtils.getClientIp(request);

        SessionLogsRequest logRequest = SessionLogsRequest.builder()
                .action(Message.LOGOUT)
                .reason(reason)
                .user(user)
                .ipAddress(clientIP)
                .build();

        new Thread(() -> {
            try {
                sessionLogsService.recordLogin(logRequest);
            } catch (Exception logEx) {
                logEx.printStackTrace();
            }
        }).start();

        return "Logout Successful";
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!user.getTempPassword().equals(changePasswordRequest.getOldPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("New Password and confirm password must be same");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setTempPassword(null);
        user.setAccType("Activated");
        userRepository.save(user);
        return "Password Changed Successfully";
    }
}