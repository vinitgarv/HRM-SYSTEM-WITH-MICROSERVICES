package com.example.repository;

import com.example.entity.AssetAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetAllotmentRepository extends JpaRepository<AssetAllotment,String>
{
        Boolean existsByAssetId(String id);

        Optional<AssetAllotment> findByAssetId(String assetId);
}
