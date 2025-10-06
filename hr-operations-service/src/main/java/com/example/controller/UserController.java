//package com.example.controller;
//
//import com.example.apiResponse.ApiResponse;
//import com.example.constants.Message;
//import com.example.dto.request.RegisterRequest;
//import com.example.dto.response.PageResponse;
//import com.example.dto.response.UserResponse;
//import com.example.mapper.UserMapper;
//import com.example.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/profile")
//    public String userProfile() {
//        return "Welcome to your profile!";
//    }
//
////    @GetMapping
////    public ApiResponse<List<UserResponse>> getAll()
////    {
////        List<UserResponse> users = userService.getAll().stream().map(user -> UserMapper.convertUsertoUserResponse(user)).toList();
////
////        ApiResponse<List<UserResponse>> response =ApiResponse.<List<UserResponse>>builder()
////                .statusCode(HttpStatus.OK.value())
////                .message("success")
////                .multiple(false)
////                .data(users)
////                .build();
////        return response;
////    }
//
//    @GetMapping
//    public ApiResponse<?> getAll(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//
//        ApiResponse<PageResponse<UserResponse>> response = ApiResponse.<PageResponse<UserResponse>>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .multiple(true)
//                .data(userService.getAll(page, size))
//                .build();
//
//        return response;
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<UserResponse> getById(@PathVariable("id") String id, @RequestHeader("username") String username) {
//            ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
//                    .statusCode(HttpStatus.OK.value())
//                    .message(Message.SUCCESS)
//                    .multiple(true)
//                    .data(UserMapper.convertUsertoUserResponse(userService.getById(id, username)))
//                    .build();
//            return response;
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<UserResponse> update(@PathVariable("id") String id, @RequestBody RegisterRequest registerRequest) {
//        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .multiple(true)
//                .data(UserMapper.convertUsertoUserResponse(userService.update(id,registerRequest)))
//                .build();
//        return response;
//    }
//}
