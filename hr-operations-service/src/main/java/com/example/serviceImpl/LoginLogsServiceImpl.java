//package com.example.serviceImpl;
//
//import com.example.constants.Message;
//import com.example.dto.request.LogRequest;
//import com.example.entity.LoginLogs;
//import com.example.repository.LoginLogsRepository;
//import com.example.service.LoginLogsService;
//import com.example.util.UtilsMethods;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//
//@Service
//public class LoginLogsServiceImpl implements LoginLogsService {
//
//    @Autowired
//    private LoginLogsRepository loginLogsRepository;
//
//    @Override
//    public void recordLogin(LogRequest request) {
//        LoginLogs logout = LoginLogs.builder()
//                .id(UtilsMethods.generateId())
//                .isActive(true)
//                .isDeleted(false)
//                .action(request.getAction())
//                .reason(request.getReason())
//                .status(request.getReason()==null?Message.SUCCESS:Message.FAILURE)
//                .user(request.getUser())
//                .ipAddress(request.getIpAddress())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        loginLogsRepository.save(logout);
//    }
//}
