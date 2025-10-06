package com.example.enums;

import com.example.exception.InvalidException;

import java.util.Arrays;

public enum AssetCondition
{
    NEW("new"),
    GOOD("good"),
    DAMAGED("damaged"),
    LOST("lost");

    private final String assetCondition;


    AssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(AssetCondition.values())
                .anyMatch(s -> s.getAssetCondition().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid asset condition : " + name);
    }

    public static AssetCondition fromString(String name) {
        return Arrays.stream(AssetCondition.values())
                .filter(s -> s.getAssetCondition().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidException("Invalid asset condition."));
    }
}
