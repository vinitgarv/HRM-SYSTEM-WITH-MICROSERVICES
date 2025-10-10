package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.RegisterRequest;
import com.moonstack.dtos.response.LoggedInUserResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.dtos.response.UserTokenResponse;
import com.moonstack.entity.User;
import com.moonstack.mapper.UserMapper;
import com.moonstack.repository.UserRepository;
import com.moonstack.response.ApiResponse;
import com.moonstack.response.PageResponse;
import com.moonstack.service.UserService;
import com.moonstack.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/profile")
    public String userProfile() {
        return "Welcome to your profile!";
    }

//    @GetMapping
//    public ApiResponse<List<UserResponse>> getAll()
//    {
//        List<UserResponse> users = userService.getAll().stream().map(user -> UserMapper.convertUsertoUserResponse(user)).toList();
//
//        ApiResponse<List<UserResponse>> response =ApiResponse.<List<UserResponse>>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("success")
//                .multiple(false)
//                .data(users)
//                .build();
//        return response;
//    }

    @GetMapping
    public ApiResponse<?> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        ApiResponse<PageResponse<UserResponse>> response = ApiResponse.<PageResponse<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.getAll(page, size))
                .build();

        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable("id") String id) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(UserMapper.convertUsertoUserResponse(userService.getById(id)))
                .build();
        return response;
    }

    @GetMapping("/find/{id}")
    public ApiResponse<User> findById(@PathVariable("id") String id) {
        ApiResponse<User> response = ApiResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userRepository.findById(id).get())
                .build();
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable("id") String id, @RequestBody RegisterRequest registerRequest) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(UserMapper.convertUsertoUserResponse(userService.update(id,registerRequest)))
                .build();
        return response;
    }

    @GetMapping("/findAll")
    public ApiResponse<List<UserResponse>> findAll() {
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.findAll())
                .build();
        return response;
    }

    @GetMapping("/usertokenresponse/{userId}/{sessionId}")
    public ApiResponse<UserTokenResponse> getUserTokenResponse(@PathVariable("userId") String userId, @PathVariable("sessionId") String sessionId) {
        ApiResponse<UserTokenResponse> response = ApiResponse.<UserTokenResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.getUserTokenResponse(userId,sessionId))
                .build();
        return response;
    }

    @GetMapping("/getAllLogedInUsers/{userId}")
    public ApiResponse<List<LoggedInUserResponse>> getAllLogedInUsers(@PathVariable("userId") String userId) {
        ApiResponse<List<LoggedInUserResponse>> response = ApiResponse.<List<LoggedInUserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.getAllLogedInUser(userId))
                .build();
        return response;
    }

    @GetMapping("/countOfLogedInUsers/{userId}")
    public ApiResponse<Integer> countOfLogedInUsers(@PathVariable("userId") String userId) {
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.getCountOfAllLogedInUsers(userId))
                .build();
        return response;
    }

    @GetMapping("/logoutALoggedInUser/{deviceId}")
    public ApiResponse<List<LoggedInUserResponse>> logoutALoggedInUser(@PathVariable("deviceId") String deviceId)
    {
        String token  = jwtTokenUtil.extractToken(request);
        String userId = jwtTokenUtil.extractUserId(token);

        userService.logoutALogedInUser(deviceId);

        ApiResponse<List<LoggedInUserResponse>> response = ApiResponse.<List<LoggedInUserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(userService.getAllLogedInUser(userId))
                .build();
        return response;
    }
}
