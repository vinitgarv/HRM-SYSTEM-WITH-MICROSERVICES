package com.example.serviceImpl;

import com.example.dto.request.AssetRequest;
import com.example.dto.request.AssetUpdateRequest;
import com.example.dto.response.AssetResponse;
import com.example.dto.response.PageResponse;
import com.example.entity.Asset;
import com.example.enums.AssetCondition;
import com.example.enums.AssetStatus;
import com.example.exception.AlreadyPresentException;
import com.example.exception.NotFoundException;
import com.example.mapper.AssetMapper;
import com.example.repository.AssetRepository;
import com.example.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService
{
    @Autowired
    private AssetRepository assetRepository;

    @Override
    public AssetResponse add(AssetRequest assetRequest)
    {
        assetRequest.validate();

        if (assetRepository.existsByAssetTag(assetRequest.getAssetTag()))
            throw new AlreadyPresentException("Asset Tag already present");

        if (assetRepository.existsBySerialNumber(assetRequest.getSerialNumber()))
            throw new AlreadyPresentException("Asset with serial number : "
                                +assetRequest.getSerialNumber()+" already present.");

        Asset asset = AssetMapper.convertAssetRequestToAsset(assetRequest);
        asset.setStatus(AssetStatus.AVAILABLE.getAssetStatus());
        assetRepository.save(asset);
        return AssetMapper.convertAssetToAssetResponse(asset);
    }

    @Override
    public AssetResponse getById(String id)
    {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset not found."));
        return AssetMapper.convertAssetToAssetResponse(asset);
    }

    @Override
    public List<AssetResponse> getAll()
    {
        List<Asset> assets = assetRepository.findAll();

        if (assets.isEmpty())
            throw new NotFoundException("No assets found");
        return assets.stream().map(AssetMapper::convertAssetToAssetResponse).toList();
    }

    @Override
    public PageResponse<AssetResponse> getAllWithPagination(Integer page, Integer size)
    {
        List<AssetResponse> responseList;
        int totalPages = 0;
        int totalElements = 0;
        if (page != null && size != null)
        {
            Pageable pageable = PageRequest.of(page, size);
            Page<Asset> userPage = assetRepository.findAll(pageable);
            responseList = userPage.get().map(AssetMapper::convertAssetToAssetResponse).toList();
            totalElements = (int) userPage.getTotalElements();
            totalPages = userPage.getTotalPages();
        }
        else
        {
            responseList = assetRepository.findAll().stream().map(AssetMapper::convertAssetToAssetResponse).toList();
            totalElements = responseList.size();
        }
        return PageResponse.<AssetResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public AssetResponse update(String id, AssetUpdateRequest assetUpdateRequest)
    {
        Asset existingAsset = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset not found."));

        assetUpdateRequest.validate();
        AssetCondition assetCondition = AssetCondition.fromString(assetUpdateRequest.getCondition());
        AssetStatus assetStatus = AssetStatus.fromString(assetUpdateRequest.getStatus());

        Optional<Asset> optional1 = assetRepository.findByAssetTag(assetUpdateRequest.getAssetTag());
        if (optional1.isPresent() && !optional1.get().getId().equals(existingAsset.getId()))
            throw new AlreadyPresentException("Asset Tag already present.");

        Optional<Asset> optional2 = assetRepository.findBySerialNumber(assetUpdateRequest.getSerialNumber());
        if (optional2.isPresent() && !optional2.get().getId().equals(existingAsset.getId()))
            throw new AlreadyPresentException("Serial Number already present.");

        existingAsset.setAssetTag(assetUpdateRequest.getAssetTag());
        existingAsset.setAssetType(assetUpdateRequest.getAssetType());
        existingAsset.setBrand(assetUpdateRequest.getBrand());
        existingAsset.setModel(assetUpdateRequest.getModel());
        existingAsset.setSerialNumber(assetUpdateRequest.getSerialNumber());
        existingAsset.setPurchaseDate(assetUpdateRequest.getPurchaseDate());
        existingAsset.setPurchaseCost(assetUpdateRequest.getPurchaseCost());
        existingAsset.setVendorName(assetUpdateRequest.getVendorName());
        existingAsset.setAssetCondition(assetCondition.getAssetCondition());
        existingAsset.setStatus(assetStatus.getAssetStatus());
        existingAsset.setLocation(assetUpdateRequest.getLocation());
        existingAsset.setWarranty(assetUpdateRequest.getWarranty());
        existingAsset.setNotes(assetUpdateRequest.getNotes());

        assetRepository.save(existingAsset);
        return AssetMapper.convertAssetToAssetResponse(existingAsset);
    }

    @Override
    public String delete(String id)
    {
        if (!assetRepository.existsById(id))
            throw new NotFoundException("Asset not found");

        assetRepository.deleteById(id);
        return "Asset deleted successfully!!";
    }

    @Override
    public Asset findById(String id)
    {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("asset not found."));
        return asset;
    }

    @Override
    public void save(Asset asset) {
        assetRepository.save(asset);
    }
}
