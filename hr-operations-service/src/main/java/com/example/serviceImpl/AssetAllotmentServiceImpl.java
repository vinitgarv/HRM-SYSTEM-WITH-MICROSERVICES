package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.response.AssetAllotmentResponse;
import com.example.dto.response.AssetResponse;
import com.example.dto.response.PageResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.Asset;
import com.example.entity.AssetAllotment;
import com.example.enums.AssetStatus;
import com.example.exception.AlreadyPresentException;
import com.example.exception.NotFoundException;
import com.example.mapper.AssetAllotmentMapper;
import com.example.mapper.AssetMapper;
import com.example.repository.AssetAllotmentRepository;
import com.example.service.AssetAllotmentService;
import com.example.service.AssetService;
import com.example.util.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetAllotmentServiceImpl implements AssetAllotmentService
{
    @Autowired
    private AssetAllotmentRepository assetAllotmentRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private AssetService assetService;

    @Override
    public AssetAllotmentResponse allotAsset(String assetId, String userId)
    {
        Asset asset = assetService.findById(assetId);

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        if (assetAllotmentRepository.existsByAssetId(assetId))
            throw new AlreadyPresentException("Asset already assigned.");

        AssetAllotment assetAllotment = AssetAllotment.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .assetId(assetId)
                .userId(userId)
                .build();
        assetAllotmentRepository.save(assetAllotment);


        asset.setStatus(AssetStatus.ASSIGNED.getAssetStatus());
        assetService.save(asset);

        AssetAllotmentResponse response = AssetAllotmentMapper.AssetAllotmentToAssetAllotmentResponse(assetAllotment);
        response.setAssetResponse(assetService.getById(assetId));
        response.setUserResponse(user);
        return response;
    }

    @Override
    public AssetAllotmentResponse update(String allotId, String assetId, String userId)
    {
        AssetAllotment assetAllotment = assetAllotmentRepository.findById(allotId)
                .orElseThrow(() -> new NotFoundException("Wrong allotment id."));

        Asset asset = assetService.findById(assetId);

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        Optional<AssetAllotment> optional = assetAllotmentRepository.findByAssetId(assetId);

        if (optional.isPresent() && !optional.get().getId().equals(assetAllotment.getId()))
            throw new AlreadyPresentException("Asset already assigned.");


        assetAllotment.setAssetId(assetId);
        assetAllotment.setUserId(userId);

        asset.setStatus(AssetStatus.ASSIGNED.getAssetStatus());
        assetService.save(asset);

        assetAllotmentRepository.save(assetAllotment);

        AssetAllotmentResponse response = AssetAllotmentMapper.AssetAllotmentToAssetAllotmentResponse(assetAllotment);
        response.setAssetResponse(assetService.getById(assetId));
        response.setUserResponse(user);
        return response;
    }

    @Override
    public AssetAllotmentResponse getById(String allotId)
    {
        AssetAllotment assetAllotment = assetAllotmentRepository.findById(allotId)
                .orElseThrow(() -> new NotFoundException("Wrong allot id"));

        AssetAllotmentResponse response = AssetAllotmentMapper.AssetAllotmentToAssetAllotmentResponse(assetAllotment);

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(assetAllotment.getUserId());
        UserResponse user = apiResponse.getData();

        response.setAssetResponse(assetService.getById(assetAllotment.getAssetId()));
        response.setUserResponse(user);

        return response;
    }

    @Override
    public List<AssetAllotmentResponse> getAll() {
        List<AssetAllotment> assetAllotments = assetAllotmentRepository.findAll();

        if (assetAllotments.isEmpty())
            throw new NotFoundException("No data found");

        List<AssetAllotmentResponse> list = assetAllotments.stream().map(aa -> {
            ApiResponse<UserResponse> apiResponse = userClient.getByUserId(aa.getUserId());
            UserResponse user = apiResponse.getData();

            return AssetAllotmentResponse.builder()
                    .id(aa.getId())
                    .assetResponse(assetService.getById(aa.getAssetId()))
                    .userResponse(user)
                    .build();

        }).toList();

        return list;
    }

    @Override
    public PageResponse<AssetAllotmentResponse> getAllWithPagination(Integer page, Integer size)
    {
        List<AssetAllotmentResponse> responseList;
        int totalPages = 0;
        int totalElements = 0;
        if (page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page, size);
            Page<AssetAllotment> userPage = assetAllotmentRepository.findAll(pageable);
            responseList = userPage.get()
                    .map(aa -> {
            ApiResponse<UserResponse> apiResponse = userClient.getByUserId(aa.getUserId());
            UserResponse user = apiResponse.getData();

            return AssetAllotmentResponse.builder()
                    .id(aa.getId())
                    .assetResponse(assetService.getById(aa.getAssetId()))
                    .userResponse(user)
                    .build();

                        }).toList();

            totalElements = (int) userPage.getTotalElements();
            totalPages = userPage.getTotalPages();
        }
        else
        {
            responseList = assetAllotmentRepository.findAll().stream()
                    .map(aa -> {
                ApiResponse<UserResponse> apiResponse = userClient.getByUserId(aa.getUserId());
                UserResponse user = apiResponse.getData();

                return AssetAllotmentResponse.builder()
                        .id(aa.getId())
                        .assetResponse(assetService.getById(aa.getAssetId()))
                        .userResponse(user)
                        .build();

                     }).toList();
            totalElements = responseList.size();
        }
        return PageResponse.<AssetAllotmentResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public String delete(String id)
    {
        AssetAllotment assetAllotment = assetAllotmentRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found."));

        Asset asset = assetService.findById(assetAllotment.getAssetId());
        asset.setStatus(AssetStatus.AVAILABLE.getAssetStatus());
        assetService.save(asset);

        assetAllotmentRepository.deleteById(id);
        return "Asset deleted successfully!!";
    }
}
