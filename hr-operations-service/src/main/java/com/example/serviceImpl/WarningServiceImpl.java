package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.constants.Message;
import com.example.dto.request.WarningRequest;
import com.example.dto.response.PageResponse;
import com.example.dto.response.UserResponse;
import com.example.dto.response.WarningResponse;
import com.example.entity.Warning;
import com.example.exception.AlreadyPresentException;
import com.example.exception.NotFoundException;
import com.example.mapper.WarningMapper;
import com.example.repository.WarningRepository;
import com.example.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarningServiceImpl implements WarningService {

    @Autowired
    private WarningRepository warningRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public WarningResponse addWarning(WarningRequest request)
    {
        request.validate();
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(request.getUserId());
        UserResponse user = apiResponse.getData();

        Warning warning = WarningMapper.convertWarningRequestToWarning(request);
        warning.setUser(user.getId());

        warningRepository.save(warning);
        return WarningMapper.convertWarningToWarningResponse(warning);
    }

    @Override
    public WarningResponse getById(String id)
    {
          Warning warning =  warningRepository.findById(id)
                  .orElseThrow(() -> new NotFoundException(Message.WARNING+Message.TAB+
                                                           Message.NOT_FOUND+Message.DOT));
          return WarningMapper.convertWarningToWarningResponse(warning);
    }

    @Override
    public PageResponse<WarningResponse> getAllWarnings(Integer page, Integer size)
    {
        List<WarningResponse> responseList;
        int totalPages =0;
        int totalElements = 0;

        if (page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page,size);
            Page<Warning> warningResponses = warningRepository.findAll(pageable);
            responseList = warningResponses.get()
                    .map(WarningMapper::convertWarningToWarningResponse)
                    .toList();
            totalElements = (int) warningResponses.getTotalElements();
            totalPages = warningResponses.getTotalPages();
        }
        else
        {
            responseList = warningRepository.findAll().stream()
                    .map(WarningMapper::convertWarningToWarningResponse)
                    .toList();
            totalElements = responseList.size();
        }
        return PageResponse.<WarningResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public WarningResponse update(String id, WarningRequest request)
    {
        Warning existing = warningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.WARNING+Message.TAB+
                                                         Message.NOT_FOUND+Message.DOT));

        request.validate();

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(request.getUserId());
        UserResponse user = apiResponse.getData();

        Optional<Warning> optional = warningRepository.findBySubjectAndWarningDateAndUser(request.getSubject(),request.getWarningDate(),user.getId());

        if (optional.isPresent() && !optional.get().getId().equals(id))
            throw new AlreadyPresentException(Message.ALREADY+Message.COMMA+Message.TAB+Message.A+
                                              Message.TAB+Message.WARNING+Message.TAB+ Message.HAVE+
                                              Message.TAB+Message.BEEN+Message.TAB+Message.ISSUED+
                                              Message.DOT);
        existing.setUser(user.getId());
        existing.setSubject(request.getSubject());
        existing.setWarningDate(request.getWarningDate());
        existing.setDescription(request.getDescription());

        warningRepository.save(existing);
        return WarningMapper.convertWarningToWarningResponse(existing);
    }

    @Override
    public String delete(String id)
    {
        Warning warning =  warningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.WARNING+Message.TAB+
                                                         Message.NOT_FOUND+Message.DOT));

        warningRepository.deleteById(id);
        return Message.WARNING+Message.DOT+Message.DELETED+
               Message.DOT+Message.SUCCESSFULLY+Message.DOT;
    }
}
