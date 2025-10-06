package com.example.enums;

import com.example.exception.InvalidException;

import java.util.Arrays;

public enum AssetStatus
{
    AVAILABLE("available"),
    ASSIGNED("assigned"),
    UNDER_MAINTENANCE("under maintenance"),
    RETIRED("retired");

    private final String assetStatus;

    AssetStatus(String assetStatus)
    {
        this.assetStatus = assetStatus ;
    }

    public String getAssetStatus()
    {
        return assetStatus;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(AssetStatus.values())
                .anyMatch(s -> s.getAssetStatus().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid asset status : " + name);
    }

    public static AssetStatus fromString(String name) {
        return Arrays.stream(AssetStatus.values())
                .filter(s -> s.getAssetStatus().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidException("Invalid asset status."));
    }
}
