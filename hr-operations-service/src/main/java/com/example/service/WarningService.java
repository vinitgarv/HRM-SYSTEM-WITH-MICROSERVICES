package com.example.service;

import com.example.dto.request.WarningRequest;
import com.example.dto.response.PageResponse;
import com.example.dto.response.WarningResponse;

import java.util.List;

public interface WarningService
{

    WarningResponse addWarning(WarningRequest request);

    WarningResponse getById(String id);

    PageResponse<WarningResponse> getAllWarnings(Integer page , Integer size);

    WarningResponse update(String id,WarningRequest request);

    String delete(String id);
}
