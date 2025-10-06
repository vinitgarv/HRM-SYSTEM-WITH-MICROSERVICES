package com.example.service;

import com.example.dto.request.TimeSheetRequest;
import com.example.dto.response.PageResponse;
import com.example.dto.response.TimeSheetResponse;

import java.util.List;

public interface TimeSheetService
{
    TimeSheetResponse add(TimeSheetRequest request);

    TimeSheetResponse getById(String id);

    PageResponse<TimeSheetResponse> getAll(Integer page, Integer size);

    TimeSheetResponse update(String id,TimeSheetRequest request);

    String delete(String id);

    TimeSheetResponse timeSheetApproved(String timeSheetId, String approverId);

    TimeSheetResponse timeSheetRejected(String timeSheetId, String approverId);

    PageResponse<TimeSheetResponse> filterByStatus(String status, Integer page, Integer size);
}
