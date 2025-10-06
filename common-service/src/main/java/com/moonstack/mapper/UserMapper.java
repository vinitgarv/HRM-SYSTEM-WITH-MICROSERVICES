package com.moonstack.mapper;

import com.moonstack.dtos.request.UserRequest;
import com.moonstack.dtos.response.RoleResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.Role;
import com.moonstack.entity.User;
import com.moonstack.utils.Helper;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper
{
    public static RoleResponse convertRoletoRoleResponse(Role role) {
        RoleResponse response = RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
        return response;
    }

    public static UserResponse convertUsertoUserResponse(User user) {
        Set<RoleResponse> roles = user.getRoles() == null ? null :
                user.getRoles().stream().map(UserMapper::convertRoletoRoleResponse).collect(Collectors.toSet());
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        response.setRoles(roles);
        return response;
    }
}
