package com.example.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetResponse
{
    private String id;
    private String assetTag;
    private String assetType;
    private String brand;
    private String model;
    private String serialNumber;

    private LocalDate purchaseDate;
    private Double purchaseCost;
    private String vendorName;

    private String status; // available, assigned, under_maintenance, retired
    private String condition; // new, good, damaged, lost

    private String location;
    private String warranty;
    private String notes;
}
