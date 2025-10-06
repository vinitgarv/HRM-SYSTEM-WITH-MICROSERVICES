package com.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class WarningResponse {
    private String id;
    private String name;
    private String subject;
    private LocalDate warningDate;
    private String description;
}
