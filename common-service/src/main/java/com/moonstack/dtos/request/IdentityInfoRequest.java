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

    private String uan;
    private String pan;
    private String aadhar;

    public void validate()
    {
        // UAN (Universal Account Number) → 12 digits
        if (uan == null || !uan.matches("^[0-9]{12}$"))
        {
            throw new RequestFailedException(Message.UAN + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        // PAN (Permanent Account Number) → 10 chars (AAAAA9999A)
        if (pan == null || !pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$"))
        {
            throw new RequestFailedException(Message.PAN + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        // Aadhaar → 12 digits, not starting with 0 or 1
        if (aadhar == null || !aadhar.matches("^[2-9]{1}[0-9]{11}$"))
        {
            throw new RequestFailedException(Message.AADHAR + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }
    }
}
