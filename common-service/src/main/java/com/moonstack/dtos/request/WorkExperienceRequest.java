package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceRequest {

    private String companyName;
    private String jobTitle;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String jobDescription;

    public void validate() {
        validateField(companyName, 2, 100, Message.COMPANY_NAME);
        validateField(jobTitle, 2, 50, Message.JOB_TITLE);
        validateDate(fromDate, Message.FROM_DATE);
        validateToDate(toDate, fromDate, Message.TO_DATE);
        validateOptionalField(jobDescription, 500, Message.JOB_DESCRIPTION);
    }

    private void validateField(String field, int minLength, int maxLength, String fieldName) {
        if (field == null || field.isEmpty()) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        if (field.length() < minLength) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }
        if (field.length() > maxLength) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.LONG + Message.DOT);
        }
    }

    private void validateOptionalField(String field, int maxLength, String fieldName) {
        if (field != null && field.length() > maxLength) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.LONG + Message.DOT);
        }
    }

    private void validateDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        if (date.isAfter(LocalDate.now())) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.CANNOT + Message.TAB +
                    Message.BE + Message.TAB + Message.FUTURE + Message.DOT);
        }
    }

    private void validateToDate(LocalDate toDate, LocalDate fromDate, String fieldName) {
        if (toDate == null) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        if (fromDate != null && toDate.isBefore(fromDate)) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.CANNOT + Message.TAB +
                    Message.BE + Message.TAB + Message.BEFORE + Message.TAB + Message.FROM_DATE + Message.DOT);
        }
        if (toDate.isAfter(LocalDate.now())) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.CANNOT + Message.TAB +
                    Message.BE + Message.TAB + Message.FUTURE + Message.DOT);
        }
    }
}
