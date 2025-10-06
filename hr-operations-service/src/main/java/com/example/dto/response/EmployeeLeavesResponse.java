package com.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class EmployeeLeavesResponse {
    private List<LeaveResponse> leaveResponses;
    private int totalLeave;
    private int approvedLeaves;
    private int rejectedLeaves;
    private int pendingLeaves;
}
