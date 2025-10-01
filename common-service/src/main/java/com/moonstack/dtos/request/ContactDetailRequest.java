package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailRequest {

    private String workPhoneNumber;
    private String personalMobile;
    private String extension;
    private String personalEmail;
    private String seatingLocation;
    private String tag;
    private String presentAddress;
    private String permanentAddress;

    // regex for 10-digit mobile (starting with 6-9)
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");

    // simple email regex
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate()
    {
        validateMobile(workPhoneNumber, Message.WORK_PHONE);
        validateMobile(personalMobile, Message.PERSONAL_MOBILE);
        validateEmail(personalEmail, Message.PERSONAL_EMAIL);
        validateAddress(presentAddress, Message.PRESENT_ADDRESS);
        validateAddress(permanentAddress, Message.PERMANENT_ADDRESS);
    }

    private void validateMobile(String mobile, String fieldName)
    {
        if (mobile == null || mobile.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (!MOBILE_PATTERN.matcher(mobile).matches())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }
    }

    private void validateEmail(String email, String fieldName)
    {
        if (email == null || email.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (!EMAIL_PATTERN.matcher(email).matches())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }
    }

    private void validateAddress(String address, String fieldName)
    {
        if (address == null || address.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (address.length() < 10)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }

        if (address.length() > 200)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.LONG + Message.DOT);
        }
    }
}
