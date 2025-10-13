package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityInfoRequest {
    private String documentType;
    private String documentNumber;
    private String fileName;
    private String fileType;

    public void validate() {

        if (documentType == null || documentType.isEmpty() ||
                !(documentType.equalsIgnoreCase("AADHAAR") ||
                        documentType.equalsIgnoreCase("PAN") ||
                        documentType.equalsIgnoreCase("UAN"))) {
            throw new RequestFailedException("Document Type" + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            throw new RequestFailedException("Document Number" + Message.TAB + Message.IS + Message.TAB + Message.REQUIRED);
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            throw new RequestFailedException("File Name" + Message.TAB + Message.IS + Message.TAB + Message.REQUIRED);
        }

        if (fileType == null || fileType.trim().isEmpty()) {
            throw new RequestFailedException("File Type" + Message.TAB + Message.IS + Message.TAB + Message.REQUIRED);
        }
    }
}
