//package com.example.mapper;
//
//import com.example.dto.response.RoleResponse;
//import com.example.dto.response.UserResponse;
//import com.example.entity.Role;
//import com.example.entity.User;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class UserMapper {
//    public static RoleResponse convertRoletoRoleResponse(Role role) {
//        RoleResponse response = RoleResponse.builder()
//                .id(role.getId())
//                .name(role.getName())
//                .build();
//        return response;
//    }
//
//    public static UserResponse convertUsertoUserResponse(User user) {
//        Set<RoleResponse> roles = user.getRoles() == null ? null :
//                user.getRoles().stream().map(UserMapper::convertRoletoRoleResponse).collect(Collectors.toSet());
//        UserResponse response = UserResponse.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .build();
//
//        response.setRoles(roles);
//        return response;
//    }
//}
