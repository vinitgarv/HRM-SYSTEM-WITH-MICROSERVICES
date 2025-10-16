package com.example.mapper;

import com.example.dto.request.AssetRequest;
import com.example.dto.request.AssetUpdateRequest;
import com.example.dto.response.AssetResponse;
import com.example.entity.Asset;
import com.example.enums.AssetCondition;
import com.example.enums.AssetStatus;
import com.example.util.UtilsMethods;

public class AssetMapper
{
    public static Asset convertAssetRequestToAsset(AssetRequest request)
    {
        AssetCondition assetCondition = AssetCondition.fromString(request.getCondition());

        return Asset.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .assetTag(request.getAssetTag())
                .assetType(request.getAssetType())
                .brand(request.getBrand())
                .model(request.getModel())
                .serialNumber(request.getSerialNumber())
                .purchaseDate(request.getPurchaseDate())
                .purchaseCost(request.getPurchaseCost())
                .vendorName(request.getVendorName())
                .assetCondition(assetCondition.getAssetCondition())
                .location(request.getLocation())
                .warranty(request.getWarranty())
                .notes(request.getNotes())
                .build();
    }

    public static Asset convertAssetUpdateRequestToAsset(AssetUpdateRequest request)
    {
        AssetCondition assetCondition = AssetCondition.fromString(request.getCondition());
        AssetStatus assetStatus = AssetStatus.fromString(request.getStatus());

        return Asset.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .assetTag(request.getAssetTag())
                .assetType(request.getAssetType())
                .brand(request.getBrand())
                .model(request.getModel())
                .serialNumber(request.getSerialNumber())
                .purchaseDate(request.getPurchaseDate())
                .purchaseCost(request.getPurchaseCost())
                .vendorName(request.getVendorName())
                .assetCondition(assetCondition.getAssetCondition())
                .status(assetStatus.getAssetStatus())
                .location(request.getLocation())
                .warranty(request.getWarranty())
                .notes(request.getNotes())
                .build();
    }

    public static AssetResponse convertAssetToAssetResponse(Asset asset)
    {

        return AssetResponse.builder()
                .id(asset.getId())
                .assetTag(asset.getAssetTag())
                .assetType(asset.getAssetType())
                .brand(asset.getBrand())
                .model(asset.getModel())
                .serialNumber(asset.getSerialNumber())
                .purchaseDate(asset.getPurchaseDate())
                .purchaseCost(asset.getPurchaseCost())
                .vendorName(asset.getVendorName())
                .status(asset.getStatus())
                .condition(asset.getAssetCondition())
                .location(asset.getLocation())
                .warranty(asset.getWarranty())
                .notes(asset.getNotes())
                .build();
    }
}
