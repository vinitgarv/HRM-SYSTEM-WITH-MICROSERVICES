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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE ASSET_ALLOTMENT SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class AssetAllotment extends AbstractPersistable
{
    private String assetId;
    private String userId;
}
