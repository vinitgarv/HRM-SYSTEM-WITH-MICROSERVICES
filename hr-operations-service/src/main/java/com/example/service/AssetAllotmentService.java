package com.example.service;

import com.example.dto.response.AssetAllotmentResponse;
import com.example.dto.response.PageResponse;

import java.util.List;

public interface AssetAllotmentService
{
    AssetAllotmentResponse allotAsset(String assetId,String userId);

    AssetAllotmentResponse update(String allotId,String assetId,String userId);

    AssetAllotmentResponse getById(String allotId);

    List<AssetAllotmentResponse> getAll();

    PageResponse<AssetAllotmentResponse> getAllWithPagination(Integer page, Integer size);

    String delete(String id);
}
