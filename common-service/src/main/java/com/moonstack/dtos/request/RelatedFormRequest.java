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
public class RelatedFormRequest {

    private String type;   // Asset, Benefit, TravelExpense, TravelRequest
    private String details;

    public void validate() {
        // Type validation
        if (type == null || type.isEmpty()) {
            throw new RequestFailedException(Message.TYPE + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (!(type.equalsIgnoreCase("Asset") ||
                type.equalsIgnoreCase("Benefit") ||
                type.equalsIgnoreCase("TravelExpense") ||
                type.equalsIgnoreCase("TravelRequest"))) {
            throw new RequestFailedException(Message.TYPE + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        // Details validation
        if (details == null || details.isEmpty()) {
            throw new RequestFailedException(Message.DETAILS + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (details.length() > 500) {
            throw new RequestFailedException(Message.DETAILS + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.LONG);
        }
    }
}
