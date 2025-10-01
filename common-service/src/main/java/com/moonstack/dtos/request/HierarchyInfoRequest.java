package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyInfoRequest {

    private String reportingManager;

    public void validate()
    {
        if (reportingManager == null || reportingManager.isEmpty())
        {
            throw new RequestFailedException(Message.REPORTING_MANAGER + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (reportingManager.length() < 3)
        {
            throw new RequestFailedException(Message.REPORTING_MANAGER + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }
    }
}

