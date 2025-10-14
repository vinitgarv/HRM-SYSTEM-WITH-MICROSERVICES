package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.AuthResponse;
import com.moonstack.entity.*;
import com.moonstack.exception.*;
import com.moonstack.repository.DeviceDataRepository;
import com.moonstack.repository.RefreshTokenRepository;
import com.moonstack.repository.UserRepository;
import com.moonstack.repository.UserSessionDataRepository;
import com.moonstack.security.CustomUserDetailsService;
import com.moonstack.service.*;
import com.moonstack.utils.DeviceUtil;
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
import java.util.Optional;
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
    private UserSessionDataRepository userSessionDataRepository;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

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

    @Autowired
    private UserService userService;

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

        String changePasswordLink = "http://localhost:5173/changepassword/"+user.getId()+"?tempPassword=" + user.getTempPassword();

        String emailBody =replacePlaceHoldersForChangePassword(user, changePasswordLink);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(request.getEmail())
                .subject("HRM System : Change Password")
                .message(emailBody)
                .build();

        new Thread(() ->
        {
            emailService.sendEmail(emailRequest);
        }).start();
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest, HttpServletRequest request)
    {
        User user = null;
        String accessToken = null;
        String refreshTokenValue = null;
        String reason = null;

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if (userService.getCountOfAllLogedInUsers(user.getId())==2)
                throw new ForbiddenException("DEVICE_LIMIT_EXCEED","Login denied: Only two devices are allowed per user.");

            // Device info
            String deviceId = DeviceUtil.generateDeviceId(request);
            String deviceName = DeviceUtil.getDeviceName(request);
            String ipAddress = IpUtils.getClientIp(request);

            // Check if device already exists for this user
            Optional<DeviceData> existingDeviceOpt = deviceDataRepository.findByDeviceIdAndUser(deviceId, user);

            DeviceData deviceData;
            if (existingDeviceOpt.isPresent())
            {
                // Update existing device
                deviceData = existingDeviceOpt.get();
                deviceData.setDeviceName(deviceName);
                deviceData.setIpAddress(ipAddress);
                deviceData.setIsActive(true);
                deviceData.setDeleted(false);

            }
            else
            {
                // Create new device
                deviceData = DeviceData.builder()
                        .id(Helper.generateId())
                        .deviceId(deviceId)
                        .deviceName(deviceName)
                        .ipAddress(ipAddress)
                        .isActive(true)
                        .deleted(false)
                        .user(user)
                        .build();
            }

            // Save the device
            deviceData = deviceDataRepository.save(deviceData);


            Optional<UserSessionData> existingUserSessionData = userSessionDataRepository.findByUserAndDeviceData(user, deviceData);

            UserSessionData userSessionData;
            if(existingUserSessionData.isPresent())
            {
                userSessionData = existingUserSessionData.get();
                userSessionData.setIsActive(true);
                userSessionData.setDeleted(false);
                userSessionData.setDeviceData(deviceData);
                userSessionData.setUser(user);
            }
            else
            {
                userSessionData = UserSessionData.builder()
                        .id(Helper.generateId())
                        .user(user)
                        .deviceData(deviceData)
                        .isActive(true)
                        .deleted(false)
                        .build();
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            accessToken = jwtTokenUtil.generateJwtToken(userDetails.getUsername(), user.getId(), roles,userSessionData,deviceData);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            refreshTokenValue = refreshToken.getToken();

            userSessionData.setAccessToken(accessToken);
            userSessionData.setRefreshToken(refreshTokenValue);

            userSessionDataRepository.save(userSessionData);

            user.getUserSessionData().add(userSessionData);
            user.getDeviceData().add(deviceData);
            userRepository.save(user);

            return AuthResponse.builder()
                    .token(accessToken)
                    .refreshToken(refreshTokenValue)
                    .build();

        }
        catch (Exception ex)
        {
            reason = ex.getMessage();
            user = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
            throw ex;
        }
        finally
        {
            final User finalUser = user;
            String clientIp = IpUtils.getClientIp(request);
            SessionLogsRequest logRequest = SessionLogsRequest.builder()
                    .action(Message.LOGIN)
                    .reason(reason)
                    .user(user)
                    .ipAddress(clientIp)
                    .build();
            if (finalUser != null)
            {
                new Thread(() ->
                {
                    try
                    {
                        sessionLogsService.recordLogin(logRequest);
                    }
                    catch (Exception logEx)
                    {
                        logEx.printStackTrace();
                    }
                }).start();
            }
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request)
    {
        String requestRefreshToken = request.getRefreshToken();

        // Fetch and validate refresh token
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new ForbiddenException("REFRESH_TOKEN_EXPIRED","Refresh Token has expired. Please log in again."));

        // Get the user and session linked to this refresh token
        User user = refreshToken.getUser();

        // Find the UserSessionData that matches this refresh token
        UserSessionData sessionData = user.getUserSessionData().stream()
                .filter(s -> requestRefreshToken.equals(s.getRefreshToken()))
                .findFirst()
                .orElseThrow(() -> new ForbiddenException("SESSION_NOT_FOUND","Refresh Token has expired. Please log in again."));

        DeviceData deviceData = sessionData.getDeviceData();

        // Load user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Generate new access token with same sessionId and device info
        String newAccessToken = jwtTokenUtil.generateJwtToken(
                userDetails.getUsername(),
                user.getId().toString(),
                roles,
                sessionData,
                deviceData
        );

        // Update session record with new access token
        sessionData.setAccessToken(newAccessToken);
        userSessionDataRepository.save(sessionData);

        // Return the new access token along with the same refresh token
        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(requestRefreshToken)
                .build();
    }


    @Override
    public String logout(String userId,String sessionId,HttpServletRequest request)
    {
        String reason =null;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserSessionData sessionData = userSessionDataRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session Data not found"));

        System.out.println("\n\n\n\n\n Device Data id : "+sessionData.getDeviceData().getDeviceId()+"\n\n\n\n\n");
        DeviceData deviceData = deviceDataRepository.findById(sessionData.getDeviceData().getId())
                .orElseThrow(() -> new NotFoundException("Device Data not found"));
        try
        {
            RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                    .orElseThrow(() -> new NotFoundException("Invalid Refresh Token"));
            refreshToken.setToken(null);
            refreshToken.setExpiryDate(null);
            refreshToken.setUpdatedAt(LocalDateTime.now());

            userSessionDataRepository.delete(sessionData);
            deviceDataRepository.delete(deviceData);

            userRepository.save(user);

            refreshTokenRepository.save(refreshToken);
        }
        catch (Exception ex)
        {
            reason = ex.getMessage();
        }

        String clientIP = IpUtils.getClientIp(request);

        SessionLogsRequest logRequest = SessionLogsRequest.builder()
                .action(Message.LOGOUT)
                .reason(reason)
                .user(user)
                .ipAddress(clientIP)
                .build();

        new Thread(() ->
        {
            try
            {
                sessionLogsService.recordLogin(logRequest);
            }
            catch (Exception logEx)
            {
                logEx.printStackTrace();
            }
        }).start();

        return "Logout Successful";
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest, String userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!user.getTempPassword().equals(changePasswordRequest.getOldPassword()))
        {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
        {
            throw new IllegalArgumentException("New Password and confirm password must be same");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setTempPassword(null);
        user.setAccType("Activated");
        userRepository.save(user);
        return "Password Changed Successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request)
    {
        User user = userService.findByEmail(request.getEmail());

        String forgotPasswordLink = "http://localhost:5173/resetPassword/"+user.getId();

        String emailBody =replacePlaceHoldersForForgotPassword(user, forgotPasswordLink);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject("HRM System : Reset Password")
                .message(emailBody)
                .build();

        new Thread(() ->
        {
            emailService.sendEmail(emailRequest);
        }).start();

        return "Email sent successfully";
    }

    @Override
    public String resetPassword(String userId, ResetPasswordRequest request)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new RequestFailedException("New Password and confirm password must be same");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        String emailBody =replacePlaceHoldersForResetPassword(user);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject("HRM System : Password Reset Successfully")
                .message(emailBody)
                .build();

        new Thread(() ->
        {
            emailService.sendEmail(emailRequest);
        }).start();

        return "Password Reset Successfully";
    }

    private String replacePlaceHoldersForChangePassword(User user, String url)
    {
        String content = changePasswordHtmlContent();
        String updatedContent = content.replace(Message.USER_NAME_PLACEHOLDER, user.getFirstName())
                .replace(Message.SENDER_NAME_PLACEHOLDER, Message.SENDER_NAME)
                .replace(Message.URL_PLACEHOLDER, url)
                .replace(Message.TEMP_PASSWORD_PLACEHOLDER, user.getTempPassword());
        return updatedContent;
    }

    private String replacePlaceHoldersForForgotPassword(User user,String url)
    {
        String content = forgotPasswordHtmlContent();
        String updatedContent = content.replace(Message.USER_NAME_PLACEHOLDER,user.getFirstName())
                .replace(Message.RESET_PASS_URL_PLACEHOLDER,url);
          return updatedContent;
    }

    private String replacePlaceHoldersForResetPassword(User user)
    {
        String content = resetPasswordHtmlContent();
        String updatedContent = content.replace(Message.USER_NAME_PLACEHOLDER,user.getFirstName());
        return updatedContent;
    }

    private  String changePasswordHtmlContent()
    {
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

    private String forgotPasswordHtmlContent()
    {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Password Reset Request - Moonstack HRM</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Arial, sans-serif;\n" +
                "            background-color: #f4f4f7;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .email-container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 30px auto;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .header {\n" +
                "            background-color: #1b5e20; /* dark green */\n" +
                "            color: #ffffff;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "        }\n" +
                "\n" +
                "        .content {\n" +
                "            padding: 30px;\n" +
                "            color: #333333;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "\n" +
                "        .content p {\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "\n" +
                "        .reset-link {\n" +
                "            color: #2e7d32;\n" +
                "            font-weight: bold;\n" +
                "            text-decoration: none;\n" +
                "            word-break: break-all;\n" +
                "        }\n" +
                "\n" +
                "        .reset-link:hover {\n" +
                "            text-decoration: underline;\n" +
                "            color: #256628;\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #999999;\n" +
                "            padding: 15px;\n" +
                "            background-color: #f4f4f7;\n" +
                "        }\n" +
                "\n" +
                "        .note {\n" +
                "            font-size: 13px;\n" +
                "            color: #777;\n" +
                "            margin-top: 15px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"email-container\">\n" +
                "    <div class=\"header\">\n" +
                "        <h1>Reset Your Password Securely</h1>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "        <p>Hi <b><USER_NAME></b>,</p>\n" +
                "\n" +
                "        <p>We received a request to reset your password for your Moonstack HRM account.</p>\n" +
                "\n" +
                "        <p>Please click the link below to reset your password:</p>\n" +
                "\n" +
                "        <p><a href=\"<RESET_PASS_URL>\" class=\"reset-link\"><RESET_PASS_URL></a></p>\n" +
                "\n" +
                "        <p>If you did not request a password reset, please ignore this email.</p>\n" +
                "\n" +
                "        <p>Best regards,<br><b>Moonstack Pvt. Ltd.</b></p>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "        &copy; 2025 HRM System. All rights reserved.\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
    
    private String resetPasswordHtmlContent()
    {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Password Changed Successfully - Moonstack HRM</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Arial, sans-serif;\n" +
                "            background-color: #f4f4f7;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .email-container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 30px auto;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .header {\n" +
                "            background-color: #1b5e20; /* dark green */\n" +
                "            color: #ffffff;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "        }\n" +
                "\n" +
                "        .content {\n" +
                "            padding: 30px;\n" +
                "            color: #333333;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "\n" +
                "        .content p {\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #999999;\n" +
                "            padding: 15px;\n" +
                "            background-color: #f4f4f7;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"email-container\">\n" +
                "    <div class=\"header\">\n" +
                "        <h1>Password Changed Successfully</h1>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "        <p>Hi <b><USER_NAME></b>,</p>\n" +
                "\n" +
                "        <p>This is a confirmation that the password for your <b>Moonstack HRM</b> account was changed successfully.</p>\n" +
                "\n" +
                "        <p>If you made this change, no further action is required.</p>\n" +
                "\n" +
                "        <p>If you did not change your password, please contact our support team immediately.</p>\n" +
                "\n" +
                "        <p>Best regards,<br><b>Moonstack Pvt. Ltd.</b></p>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "        &copy; 2025 HRM System. All rights reserved.\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}