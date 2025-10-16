package com.moonstack.enums;

import com.moonstack.exception.InvalidSessionException;
import com.moonstack.exception.RequestFailedException;

import java.util.Arrays;

public enum RolesEnum
{

    USER("user"),
    ADMIN("admin"),
    SUPER_ADMIN("super admin"),
    HR("hr");

    private final String role;


    RolesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(RolesEnum.values())
                .anyMatch(s -> s.getRole().equalsIgnoreCase(name));

        if (!isValid)
            throw new RequestFailedException("Invalid role : " + name);
    }

    public static RolesEnum fromString(String name) {
        return Arrays.stream(RolesEnum.values())
                .filter(s -> s.getRole().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RequestFailedException("Invalid role."));
    }
}
