package com.moonstack.service;

import com.moonstack.dtos.request.SessionLogsRequest;
import com.moonstack.dtos.response.SessionLogsResponse;
import com.moonstack.response.PageResponse;

public interface SessionLogsService
{
    void recordLogin(SessionLogsRequest request);
}
