package com.moonstack.enums;

import com.moonstack.exception.InvalidException;

import java.util.Arrays;

public enum PurchaseStatus
{
    PAID("paid"),
    UNPAID("unpaid"),
    RETURNED("returned");

    private final String purchaseStatus;


    PurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(PurchaseStatus.values())
                .anyMatch(s -> s.getPurchaseStatus().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid Purchase Status : " + name);
    }

    public static PurchaseStatus fromString(String name) {
        return Arrays.stream(PurchaseStatus.values())
                .filter(s -> s.getPurchaseStatus().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidException("Invalid Purchase Status."));
    }
}
