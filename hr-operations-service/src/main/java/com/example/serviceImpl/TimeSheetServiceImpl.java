package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.constants.Message;
import com.example.dto.request.TimeSheetRequest;
import com.example.dto.response.PageResponse;
import com.example.dto.response.TimeSheetResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.TimeSheet;
import com.example.exception.AlreadyPresentException;
import com.example.exception.NotFoundException;
import com.example.mapper.TimeSheetMapper;
import com.example.repository.TimeSheetRepository;
import com.example.service.TimeSheetService;
import com.example.util.SpecificationFilters.TimeSheetFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSheetServiceImpl implements TimeSheetService
{
    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public TimeSheetResponse add(TimeSheetRequest request)
    {
        request.validate();
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(request.getUserId());
        UserResponse user = apiResponse.getData();

        if (timeSheetRepository.existsByDatePostedAndUser(request.getDatePosted(), request.getUserId()))
            throw new AlreadyPresentException(Message.TIMESHEET+Message.TAB+Message.ALREADY+
                                              Message.TAB+Message.MAINTAINED+Message.TAB+
                                              Message.FOR+Message.TAB+request.getDatePosted());

        TimeSheet timeSheet = TimeSheetMapper.convertTimeSheetRequestToTimeSheet(request);
        timeSheet.setStatus(Message.PENDING);
        timeSheet.setUser(user.getId());

        timeSheetRepository.save(timeSheet);

        return TimeSheetMapper.convertTimeSheetToTimeSheetResponse(timeSheet);
    }

    @Override
    public TimeSheetResponse getById(String id)
    {
        TimeSheet timeSheet = timeSheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.TIMESHEET+Message.TAB+
                                                         Message.NOT_FOUND+Message.DOT));
        return TimeSheetMapper.convertTimeSheetToTimeSheetResponse(timeSheet);
    }

    @Override
    public PageResponse<TimeSheetResponse> getAll(Integer page, Integer size)
    {
        List<TimeSheetResponse> responseList;
        int totalPages =0;
        int totalElements = 0;

        if(page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page,size);
            Page<TimeSheet> timeSheets = timeSheetRepository.findAll(pageable);
            responseList = timeSheets.get()
                            .map(TimeSheetMapper::convertTimeSheetToTimeSheetResponse)
                            .toList();
            totalElements = (int) timeSheets.getTotalElements();
            totalPages = timeSheets.getTotalPages();
        }
        else
        {
            responseList = timeSheetRepository.findAll().stream()
                            .map(TimeSheetMapper::convertTimeSheetToTimeSheetResponse)
                            .toList();
            totalElements = responseList.size();
        }
        return PageResponse.<TimeSheetResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public TimeSheetResponse update(String id, TimeSheetRequest request)
    {
        TimeSheet existing = timeSheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.TIMESHEET+Message.TAB+
                        Message.NOT_FOUND+Message.DOT));

        request.validate();

        Optional<TimeSheet> optional = timeSheetRepository.findByDatePostedAndUser(request.getDatePosted(),request.getUserId());

        if (optional.isPresent() && !optional.get().getId().equals(id))
            throw new AlreadyPresentException(Message.TIMESHEET+Message.TAB+Message.ALREADY+
                    Message.TAB+Message.MAINTAINED+Message.TAB+
                    Message.FOR+Message.TAB+request.getDatePosted());

        existing.setDatePosted(request.getDatePosted());
        existing.setHours(request.getHours());
        existing.setDescription(request.getDescription());
        existing.setStatus(Message.UPDATED);
        existing.setUser(request.getUserId());
        existing.setApprovalDate(null);
        existing.setApprovedBy(null);

        timeSheetRepository.save(existing);
        return TimeSheetMapper.convertTimeSheetToTimeSheetResponse(existing);
    }

    @Override
    public String delete(String id)
    {
        TimeSheet timeSheet = timeSheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.TIMESHEET+Message.TAB+
                        Message.NOT_FOUND+Message.DOT));
        timeSheetRepository.deleteById(id);
        return Message.TIMESHEET+Message.TAB+Message.DELETED+
               Message.TAB+Message.SUCCESSFULLY+Message.DOT;
    }

    @Override
    public TimeSheetResponse timeSheetApproved(String timeSheetId, String approverId)
    {
        TimeSheet timeSheet = timeSheetRepository.findById(timeSheetId)
                .orElseThrow(() -> new NotFoundException(Message.TIMESHEET+Message.TAB+
                        Message.NOT_FOUND+Message.DOT));

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(approverId);
        UserResponse user = apiResponse.getData();

        timeSheet.setApprovedBy(user.getFirstName()+" "+user.getLastName());
        timeSheet.setApprovalDate(LocalDate.now());
        timeSheet.setStatus(Message.APPROVED);

        timeSheetRepository.save(timeSheet);
        return TimeSheetMapper.convertTimeSheetToTimeSheetResponse(timeSheet);
    }

    @Override
    public TimeSheetResponse timeSheetRejected(String timeSheetId, String approverId) {
        TimeSheet timeSheet = timeSheetRepository.findById(timeSheetId)
                .orElseThrow(() -> new NotFoundException(Message.TIMESHEET+Message.TAB+
                        Message.NOT_FOUND+Message.DOT));

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(approverId);
        UserResponse user = apiResponse.getData();

        timeSheet.setApprovedBy(user.getFirstName()+" "+user.getLastName());
        timeSheet.setApprovalDate(LocalDate.now());
        timeSheet.setStatus(Message.REJECTED);

        timeSheetRepository.save(timeSheet);
        return TimeSheetMapper.convertTimeSheetToTimeSheetResponse(timeSheet);
    }

    @Override
    public PageResponse<TimeSheetResponse> filterByStatus(String status, Integer page, Integer size)
    {
        List<TimeSheetResponse> responseList;
        int totalPages = 0;
        int totalElements = 0;

        Specification<TimeSheet> specification = Specification.where(TimeSheetFilters.hasStatus(status));

        if (page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page, size);
            Page<TimeSheet> timeSheets = timeSheetRepository.findAll(specification, pageable);

            responseList = timeSheets.get()
                    .map(TimeSheetMapper::convertTimeSheetToTimeSheetResponse)
                    .toList();

            totalElements = (int) timeSheets.getTotalElements();
            totalPages = timeSheets.getTotalPages();
        }
        else
        {
            responseList = timeSheetRepository.findAll(specification).stream()
                    .map(TimeSheetMapper::convertTimeSheetToTimeSheetResponse)
                    .toList();

            totalElements = responseList.size();
        }

        return PageResponse.<TimeSheetResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

}
