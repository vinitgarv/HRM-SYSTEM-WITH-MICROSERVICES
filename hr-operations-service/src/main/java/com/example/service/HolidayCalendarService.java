package com.example.service;

import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.dto.response.PageResponse;

public interface HolidayCalendarService {

    HolidayCalendarResponse add(HolidayCalendarRequest request);

    HolidayCalendarResponse getById(String id);

    PageResponse<HolidayCalendarResponse> getAllHolidays(Integer page, Integer size);

    HolidayCalendarResponse update(String id,HolidayCalendarRequest request);

    String delete(String id);
}
