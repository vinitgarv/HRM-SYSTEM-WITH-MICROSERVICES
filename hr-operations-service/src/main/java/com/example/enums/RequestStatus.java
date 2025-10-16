package com.example.enums;

import com.example.exception.InvalidException;

import java.util.Arrays;

public enum RequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String requestStatus;

    RequestStatus(String requestStatus) {
        this.requestStatus = requestStatus ;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(RequestStatus.values())
                .anyMatch(s -> s.getRequestStatus().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid request status : " + name);
    }

    public static RequestStatus fromString(String name) {
        return Arrays.stream(RequestStatus.values())
                .filter(s -> s.getRequestStatus().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidException("Invalid request status."));
    }
}
