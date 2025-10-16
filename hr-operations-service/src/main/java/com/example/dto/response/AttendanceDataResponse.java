package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDataResponse
{
    private String userId;
    private String username;
    private List<AttendanceList> attendanceList;
    private String totalWorkTime;
    private String totalBreakTime;
    private String totalLeaves;
}
