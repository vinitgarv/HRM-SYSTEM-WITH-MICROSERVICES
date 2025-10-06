package com.example.serviceImpl;

import com.example.constants.Message;
import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.dto.response.PageResponse;
import com.example.dto.response.WarningResponse;
import com.example.entity.HolidayCalendar;
import com.example.entity.Warning;
import com.example.exception.AlreadyPresentException;
import com.example.exception.NotFoundException;
import com.example.mapper.HolidayCalendarMapper;
import com.example.mapper.WarningMapper;
import com.example.repository.HolidayCalendarRepository;
import com.example.service.HolidayCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayCalendarServiceImpl implements HolidayCalendarService
{
    @Autowired
    private HolidayCalendarRepository repository;

    @Override
    public HolidayCalendarResponse add(HolidayCalendarRequest request)
    {
        request.validate();

        HolidayCalendar holidayCalendar = HolidayCalendarMapper.holidayCalendarRequestToHolidayCalendar(request);

        if (repository.findByDate(request.getDate()).isPresent())
            throw new AlreadyPresentException(Message.HOLIDAY+Message.TAB+Message.ALREADY+
                                              Message.TAB+Message.PRESENT+Message.DOT);

        repository.save(holidayCalendar);
        return HolidayCalendarMapper.holidayCalendarToHolidayCalendarResponse(holidayCalendar);
    }

    @Override
    public HolidayCalendarResponse getById(String id)
    {
        HolidayCalendar holidayCalendar = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No data found"));
        return HolidayCalendarMapper.holidayCalendarToHolidayCalendarResponse(holidayCalendar);
    }

    @Override
    public PageResponse<HolidayCalendarResponse> getAllHolidays(Integer page, Integer size)
    {
        List<HolidayCalendarResponse> responseList;
        int totalElements = 0;
        int totalPages= 0;

        if (page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page,size);
            Page<HolidayCalendar>  holidayCalendars = repository.findAll(pageable);
            responseList = holidayCalendars.get()
                    .map(HolidayCalendarMapper::holidayCalendarToHolidayCalendarResponse)
                    .toList();
            totalElements = (int) holidayCalendars.getTotalElements();
            totalPages = holidayCalendars.getTotalPages();
        }
        else
        {
            responseList = repository.findAll().stream()
                    .map(HolidayCalendarMapper::holidayCalendarToHolidayCalendarResponse)
                    .toList();
            totalElements = responseList.size();
        }
        return PageResponse.<HolidayCalendarResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public HolidayCalendarResponse update(String id, HolidayCalendarRequest request)
    {
        HolidayCalendar existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No data found"));

        request.validate();

        Optional<HolidayCalendar> optional = repository.findByDate(request.getDate());

        if (optional.isPresent() && !optional.get().getId().equals(id))
            throw new AlreadyPresentException(Message.HOLIDAY+Message.TAB+Message.ALREADY+
                                              Message.TAB+Message.PRESENT+Message.DOT);

        existing.setDate(request.getDate());
        existing.setDay(request.getDate().getDayOfWeek().toString());
        existing.setRemark(request.getRemark());
        existing.setType(request.getType());

        repository.save(existing);
        return HolidayCalendarMapper.holidayCalendarToHolidayCalendarResponse(existing);
    }

    @Override
    public String delete(String id)
    {
        HolidayCalendar existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No data found"));

        repository.deleteById(id);
        return "Deleted Successfully.";
    }
}
