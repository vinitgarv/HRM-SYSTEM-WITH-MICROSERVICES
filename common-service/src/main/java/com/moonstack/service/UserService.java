package com.moonstack.service;

import com.moonstack.dtos.request.RegisterRequest;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.User;
import com.moonstack.response.PageResponse;

import java.util.List;

public interface UserService
{
    PageResponse<UserResponse> getAll(Integer page, Integer size);

    User getById(String id);

    User update(String id, RegisterRequest registerRequest);

    User findById(String id);

    void add(User user);

    boolean userExists(String userId);

    List<UserResponse> findAll();

}
