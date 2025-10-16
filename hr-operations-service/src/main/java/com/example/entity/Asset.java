package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE ASSET SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class Asset extends AbstractPersistable
{
    private String assetTag;
    private String assetType;
    private String brand;
    private String model;
    private String serialNumber;

    private LocalDate purchaseDate;
    private Double purchaseCost;
    private String vendorName;

    private String status; // available, assigned, under_maintenance, retired
    private String assetCondition; // new, good, damaged, lost

    private String location;
    private String warranty;
    private String notes;

}
