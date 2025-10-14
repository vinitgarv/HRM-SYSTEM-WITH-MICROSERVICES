package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.LoggedInUserResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.dtos.response.UserTokenResponse;
import com.moonstack.entity.DeviceData;
import com.moonstack.entity.User;
import com.moonstack.entity.UserSessionData;
import com.moonstack.exception.AlreadyPresentException;
import com.moonstack.exception.NotFoundException;
import com.moonstack.exception.UnauthorizedException;
import com.moonstack.mapper.UserMapper;
import com.moonstack.repository.DeviceDataRepository;
import com.moonstack.repository.UserRepository;
import com.moonstack.repository.UserSessionDataRepository;
import com.moonstack.response.PageResponse;
import com.moonstack.service.UserService;
import com.moonstack.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private UserSessionDataRepository userSessionDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    public PageResponse<UserResponse> getAll(Integer page, Integer size) {
        List<UserResponse> responseList;
        int totalPages = 0;
        int totalElements = 0;
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAll(pageable);
            responseList = userPage.get().map(UserMapper::convertUsertoUserResponse).toList();
            totalElements = (int) userPage.getTotalElements();
            totalPages = userPage.getTotalPages();
        } else {
            responseList = userRepository.findAll().stream().map(UserMapper::convertUsertoUserResponse).toList();
            totalElements = responseList.size();
        }
        return PageResponse.<UserResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public User getById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.USER + Message.TAB +
                                                         Message.NOT_FOUND + Message.DOT));
        return user;
    }

    @Override
    public User update(String id, RegisterRequest registerRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.USER+Message.TAB+Message.NOT_FOUND+Message.DOT));

        Optional<User> optionalUser =  userRepository.findByEmail(registerRequest.getEmail());

        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(id))
            throw new AlreadyPresentException(Message.EMAIL+Message.ALREADY+Message.PRESENT);

        existingUser.setEmail(registerRequest.getEmail());
        existingUser.setPassword(existingUser.getPassword());
        return null;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void add(User user) {
       userRepository.save(user);
    }

    @Override
    public boolean userExists(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public List<UserResponse> findAll() {

        List<User> users  = userRepository.findAll();
        if (users.isEmpty())
            throw new NotFoundException("Users not found");

        return users.stream().map(UserMapper::convertUsertoUserResponse).toList();
    }

    @Override
    public UserTokenResponse getUserTokenResponse(String userId,String sessionId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Message.USER+Message.TAB+Message.NOT_FOUND+Message.DOT));

        UserSessionData sessionData = user.getUserSessionData()
                .stream()
                .peek(s -> log.info("Checking session: {} for user {}", s.getId(), s.getUser().getId()))
                .filter(s -> s.getId().equals(sessionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "No session data found for userId=" + userId + " and sessionId=" + sessionId));

        UserTokenResponse userTokenResponse =  UserMapper.convertUserToUserTokenResponse(user);
        userTokenResponse.setSessionId(sessionData.getId());
        userTokenResponse.setAccessToken(sessionData.getAccessToken());
        return userTokenResponse;
    }

    @Override
    public List<LoggedInUserResponse> getAllLogedInUser(String userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Message.USER+Message.TAB+Message.NOT_FOUND+Message.DOT));

        List<LoggedInUserResponse> logedInUsers = user.getUserSessionData().stream()
                .map(s ->{
                    return LoggedInUserResponse.builder()
                            .deviceId(s.getDeviceData().getId())
                            .deviceName(s.getDeviceData().getDeviceName())
                            .build();
                }).toList();

        return logedInUsers;
    }

    @Override
    public Integer getCountOfAllLogedInUsers(String userId)
    {
        return getAllLogedInUser(userId).size();
    }

    @Override
    public String logoutALogedInUser(String deviceId)
    {
        DeviceData deviceData = deviceDataRepository.findById(deviceId)
                .orElseThrow(() -> new NotFoundException("Device not found"));

        UserSessionData sessionData = userSessionDataRepository.findByDeviceData(deviceData)
                .orElseThrow(() -> new NotFoundException("User session data not found"));

        String token  = jwtTokenUtil.extractToken(httpServletRequest);
        String userId = jwtTokenUtil.extractUserId(token);

        if (!deviceData.getUser().getId().equals(userId))
            throw new UnauthorizedException("Invalid token");

        deviceDataRepository.delete(deviceData);
        userSessionDataRepository.delete(sessionData);

        return deviceData.getUser().getId();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
