package com.example.repository;

import com.example.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset,String>
{
    Boolean existsByAssetTag(String assetTag);

    Boolean existsBySerialNumber(String serialNumber);

    Optional<Asset> findByAssetTagAndSerialNumber(String assetTag , String SerialNumber);

    Optional<Asset> findByAssetTag(String assetTag);
    Optional<Asset> findBySerialNumber(String serialNumber);
}
