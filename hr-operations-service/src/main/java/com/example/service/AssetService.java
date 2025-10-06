package com.example.service;

import com.example.dto.request.AssetRequest;
import com.example.dto.request.AssetUpdateRequest;
import com.example.dto.response.AssetResponse;
import com.example.dto.response.PageResponse;
import com.example.entity.Asset;


import java.util.List;

public interface AssetService
{
    AssetResponse add(AssetRequest assetRequest);

    AssetResponse getById(String id);

    List<AssetResponse> getAll();

    PageResponse<AssetResponse> getAllWithPagination(Integer page, Integer size);

    AssetResponse update(String id, AssetUpdateRequest assetUpdateRequest);

    String delete(String id);

    Asset findById(String id);
    void save(Asset asset);
}
