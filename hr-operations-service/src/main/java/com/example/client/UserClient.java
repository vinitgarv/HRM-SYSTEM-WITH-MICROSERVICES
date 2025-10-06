package com.example.client;

import com.example.apiResponse.ApiResponse;
import com.example.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COMMON-SERVICE", path = "/user")
public interface UserClient {

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getByUserId(@PathVariable("id") String id);

    @GetMapping("/findAll")
    ApiResponse<List<UserResponse>> findAllUsers();
}
