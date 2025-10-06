package com.example.mapper;

import com.example.dto.response.AssetAllotmentResponse;
import com.example.entity.AssetAllotment;

public class AssetAllotmentMapper
{
    public static AssetAllotmentResponse AssetAllotmentToAssetAllotmentResponse(AssetAllotment assetAllotment)
    {
       return AssetAllotmentResponse.builder()
               .id(assetAllotment.getId())
               .build();
    }
}
