//package com.moonstack.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.moonstack.constants.Message;
//import com.moonstack.dtos.request.ChangePasswordRequest;
//import com.moonstack.dtos.request.UserRequest;
//import com.moonstack.dtos.request.UserUpdateRequest;
//import com.moonstack.dtos.response.UserResponse;
//import com.moonstack.response.PageResponse;
//import com.moonstack.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//public class UserControllerTest
//{
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private UserRequest userRequest;
//    private UserResponse userResponse;
//
//    @BeforeEach
//    void setUp()
//    {
//        userRequest = UserRequest.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .build();
//
//        userResponse = UserResponse.builder()
//                .id("1")
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .build();
//    }
//
//    @Test
//    void testAdd() throws Exception
//    {
//        when(userService.add(userRequest)).thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value(Message.SUCCESS))
//                .andExpect(jsonPath("$.data.email").value("test@example.com"));
//    }
//
//
//    @Test
//    void testGetUserById() throws Exception {
//        when(userService.getById("1")).thenReturn(userResponse);
//
//        mockMvc.perform(get("/api/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.data.firstName").value("John"));
//    }
//
//    @Test
//    void testGetAllUsers() throws Exception {
//        PageResponse<UserResponse> pageResponse = PageResponse.<UserResponse>builder()
//                .data(Collections.emptyList())
//                .elements(0)
//                .totalPages(0)
//                .pageNo(0)
//                .limit(10)
//                .build();
//
//        when(userService.getAll(0, 10)).thenReturn(pageResponse);
//
//        mockMvc.perform(get("/api/user")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.multiple").value(true));
//    }
//
//    @Test
//    void testUpdateUser() throws Exception {
//        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
//                .firstName("Jane")
//                .lastName("Smith")
//                .password("newpass")
//                .build();
//
//        UserResponse updated = UserResponse.builder()
//                .id("1")
//                .email("test@example.com")
//                .firstName("Jane")
//                .lastName("Smith")
//                .build();
//
//        when(userService.update("1", updateRequest)).thenReturn(updated);
//
//        mockMvc.perform(put("/api/user/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.firstName").value("Jane"))
//                .andExpect(jsonPath("$.data.lastName").value("Smith"));
//    }
//
//    @Test
//    void testToggleActiveInactive() throws Exception {
//        UserResponse response = UserResponse.builder()
//                .id("1")
//                .email("test@example.com")
//                .active(false)
//                .build();
//
//        when(userService.userActiveInactive("1")).thenReturn(response);
//
//        mockMvc.perform(patch("/api/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.active").value(false));
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        when(userService.delete("1")).thenReturn("User deleted successfully.");
//
//        mockMvc.perform(delete("/api/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("User deleted successfully."));
//    }
//
//    @Test
//    void testChangePassword() throws Exception {
//        ChangePasswordRequest req = ChangePasswordRequest.builder()
//                .oldPassword("oldPass")
//                .newPassword("newPass")
//                .confirmPassword("newPass")
//                .build();
//
//        when(userService.changePassword("1", req)).thenReturn("Password changed successfully.");
//
//        mockMvc.perform(put("/api/user/changePassword/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("Password changed successfully."));
//    }
//
//    @Test
//    void testValidateUser() throws Exception
//    {
//        String email = "sachin@gmail.com";
//
//        when(userService.validateUser(email)).thenReturn(true);
//
//        mockMvc.perform(get("/api/user/validate/" + email)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value(Message.SUCCESS))
//                .andExpect(jsonPath("$.data").value(true));
//    }
//}
