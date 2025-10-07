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
import com.moonstack.service.*;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService
{
    @Autowired
    private AuthenticationManager authenticationManager;

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

    @Autowired
    private EmailService emailService;

    @Override
    public void register(RegisterRequest request)
    {
        request.validate();
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
        user.setAccType(Message.INITIATED);

        if (request.getRoles() != null) {
            for (String roleName : request.getRoles()) {
                Role r = new Role();
                r.setId(Helper.generateId());
                r.setName(roleName);
                user.addRole(r);
            }
        }

        String changePasswordLink = "http://localhost:5173/changepassword/"+user.getId()+"?tempPassword=" + user.getTempPassword();

        String emailBody =replacePlaceHolders(user, changePasswordLink);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(request.getEmail())
                .subject("HRM System : Change Password")
                .message(emailBody)
                .build();

        new Thread(() -> {
            emailService.sendEmail(emailRequest);
        }).start();
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

    private String replacePlaceHolders(User user, String url) {
        String content = getHtmlContent();
        String updatedContent = content.replace(Message.USER_NAME_PLACEHOLDER, user.getFirstName())
                .replace(Message.SENDER_NAME_PLACEHOLDER, Message.SENDER_NAME)
                .replace(Message.URL_PLACEHOLDER, url)
                .replace(Message.TEMP_PASSWORD_PLACEHOLDER, user.getTempPassword());
        return updatedContent;
    }
    private  String getHtmlContent(){
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "  <title>Activate your account</title>\n" +
                "  <style>\n" +
                "    body { font-family: Arial, sans-serif; margin:0; padding:0; background:#f4f6f8; }\n" +
                "    .container { width:100%; max-width:600px; margin:32px auto; background:#ffffff; border-radius:8px; box-shadow:0 2px 6px rgba(0,0,0,0.06); overflow:hidden; }\n" +
                "    \n" +
                "    /* Header with green background */\n" +
                "    .header { padding:20px 24px; background:#1D6F0B; color:#fff; }\n" +
                "    .header h1 { margin:0; font-size:20px; font-weight:600; color:#fff; }\n" +
                "    \n" +
                "    .content { padding:24px; color:#333333; line-height:1.45; }\n" +
                "    \n" +
                "    /* Button with green background + white text */\n" +
                "    .btn-wrap { text-align:center; margin:20px 0; }\n" +
                "    .btn {\n" +
                "      display:inline-block;\n" +
                "      text-decoration:none;\n" +
                "      padding:12px 24px;\n" +
                "      border-radius:6px;\n" +
                "      font-weight:600;\n" +
                "      border:0;\n" +
                "      cursor:pointer;\n" +
                "      background:#1D6F0B;\n" +
                "      color:#ffffff !important;\n" +
                "    }\n" +
                "\n" +
                "    /* Info box */\n" +
                "    .info { background:#f8fafc; border:1px solid #eef3fb; padding:12px; border-radius:6px; font-family:monospace; word-break:break-all; }\n" +
                "    \n" +
                "    /* Footer */\n" +
                "    .footer { font-size:12px; color:#777; padding:16px 24px; text-align:center; }\n" +
                "    \n" +
                "    /* Links in green */\n" +
                "    a.small { color:#1D6F0B; text-decoration:none; font-size:13px; }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"container\" role=\"article\" aria-roledescription=\"email\">\n" +
                "    <div class=\"header\">\n" +
                "      <h1>Welcome, <USER_NAME></h1>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "      <p>Hello <strong><USER_NAME></strong>,</p>\n" +
                "\n" +
                "      <p>Please change your password using the link below. For security, we've provided a temporary password you can use to sign in and then change your password.</p>\n" +
                "\n" +
                "      <div class=\"btn-wrap\">\n" +
                "        <a href=\"<URL>\" class=\"btn\" target=\"_blank\" rel=\"noopener\">Activate account</a>\n" +
                "      </div>\n" +
                "\n" +
                "      <p>If the button doesn't work, open the following link in your browser:</p>\n" +
                "      <p><a class=\"small\" href=\"<URL>\" target=\"_blank\" rel=\"noopener\"><URL></a></p>\n" +
                "\n" +
                "      <p>Temporary password:</p>\n" +
                "      <div class=\"info\"><TEMP_PASSWORD></div>\n" +
                "\n" +
                "      <p>If you didn't request this, please ignore this email.</p>\n" +
                "      <p>Regards,<br><strong><SENDER_NAME></strong></p>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "      <SENDER_NAME> — Please do not reply to this automated email.\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}