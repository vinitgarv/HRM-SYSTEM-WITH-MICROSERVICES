//package com.moonstack.transformer;
//
//import com.moonstack.dtos.request.SessionLogsRequest;
//import com.moonstack.dtos.response.SessionLogsResponse;
//import com.moonstack.entity.SessionLogs;
//import com.moonstack.utils.Helper;
//
//public class SessionLoginTransformer
//{
//    public static SessionLogs convertSessionLoginRequestToSessionLogin(SessionLogsRequest request)
//    {
//
//        SessionLogs sessionLogin = SessionLogs.builder()
//                .id(Helper.generateId())
//                .status((request.getReason()) == null ? "Success":"Failed")
//                .reason(request.getReason())
//                .action(request.getAction())
//                .isActive(true)
//                .deleted(false)
//                .build();
//        return sessionLogin;
//    }
//
//    public static SessionLogsResponse convertSessionLoginToSessionLoginResponse(SessionLogs sessionLogin)
//    {
//        SessionLogsResponse response = SessionLogsResponse.builder()
//                .id(sessionLogin.getId())
//                .username(sessionLogin.getUserName())
//                .status(sessionLogin.getStatus())
//                .remainingAttempt(sessionLogin.getRemainingAttempt())
//                .reason(sessionLogin.getReason())
//                .action(sessionLogin.getAction())
//                .build();
//        return response;
//    }
//}
//
