//package com.moonstack.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.moonstack.constants.Message;
//import com.moonstack.dtos.response.SessionLoginResponse;
//import com.moonstack.response.PageResponse;
//import com.moonstack.service.AuthService;
//import com.moonstack.service.SessionLogsService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.hamcrest.Matchers.is;
//import static reactor.core.publisher.Mono.when;
//
//@WebMvcTest(AuthController.class)
//public class AuthControllerTest
//{
//    @MockBean
//    private AuthService authService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private SessionLogsService sessionLoginService;
//
//
//    @Test
//    void testLogin() throws Exception
//    {
//        when(authService.login(anyInt())).thenReturn(Message.SUCCESS);
//
//        mockMvc.perform(post("/login/2")
//                        .header("access-token","mock-token")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode",is(200)))
//                .andExpect(jsonPath("$.message", is(Message.SUCCESS)))
//                .andExpect(jsonPath("$.data", is("success")));
//    }
//
//    @Test
//    void testLogoutSuccess() throws Exception {
//        when(authService.logout(2)).thenReturn("success");
//
//        mockMvc.perform(post("/logout/2")
//                        .header("access-token", "dummy-token")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data").value("success"));
//    }
//
//    @Test
//    void testGetAll() throws Exception {
//        PageResponse<SessionLoginResponse> pageResponse = PageResponse.<SessionLoginResponse>builder()
//                .data(Collections.emptyList())
//                .elements(0)
//                .totalPages(0)
//                .pageNo(0)
//                .limit(10)
//                .build();
//
//        when(sessionLoginService.getAll(0, 10)).thenReturn(pageResponse);
//
//        mockMvc.perform(get("/getAll")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value(Message.SUCCESS))
//                .andExpect(jsonPath("$.multiple").value(true));
//    }
//
//    @Test
//    void testGetById() throws Exception {
//        SessionLoginResponse response = SessionLoginResponse.builder()
//                .username("Rererea123")
//                .action("LOGIN")
//                .reason("success")
//                .build();
//
//        when(sessionLoginService.getById("abc123")).thenReturn(response);
//
//        mockMvc.perform(get("/abc123")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value(Message.SUCCESS))
//                .andExpect(jsonPath("$.data.username").value("Rererea123"))
//                .andExpect(jsonPath("$.data.action").value("LOGIN"));
//    }
//
//}
