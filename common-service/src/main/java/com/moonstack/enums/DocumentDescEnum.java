package com.moonstack.enums;

import com.moonstack.exception.RequestFailedException;

import java.util.Arrays;

public enum DocumentDescEnum {

    TENTH("10th"),
    TWELFTH("12th"),
    UG("ug"),
    PG("pg"),
    AADHAAR("aadhaar"),
    UAN("uan"),
    PAN("pan"),
    PROFILE_PHOTO("profile_photo"),
    RESUME("Resume");


    private final String documentDesc;


    DocumentDescEnum(String documentDesc) {
        this.documentDesc = documentDesc;
    }

    public String getDocumentDesc() {
        return documentDesc;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(DocumentDescEnum.values())
                .anyMatch(s -> s.getDocumentDesc().equalsIgnoreCase(name));

        if (!isValid)
            throw new RequestFailedException("Invalid Document Desc Name : " + name);
    }

    public static DocumentDescEnum fromString(String name) {
        return Arrays.stream(DocumentDescEnum.values())
                .filter(s -> s.getDocumentDesc().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RequestFailedException("Invalid Document Name."));
    }
}
