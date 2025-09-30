package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.SessionLogsRequest;
import com.moonstack.dtos.response.SessionLogsResponse;
import com.moonstack.entity.SessionLogs;
import com.moonstack.exception.NotFoundException;
import com.moonstack.repository.SessionLogsRepository;
import com.moonstack.response.PageResponse;
import com.moonstack.service.SessionLogsService;
import com.moonstack.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionLogsServiceImpl implements SessionLogsService
{
    @Autowired
    private SessionLogsRepository sessionLogsRepository;

    @Override
    public void recordLogin(SessionLogsRequest request) {
        SessionLogs logout = SessionLogs.builder()
                .id(Helper.generateId())
                .isActive(true)
                .deleted(false)
                .action(request.getAction())
                .reason(request.getReason())
                .status(request.getReason()==null?Message.SUCCESS:Message.FAILURE)
                .user(request.getUser())
                .ipAddress(request.getIpAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sessionLogsRepository.save(logout);
    }
}
