package com.moonstack.dtos.request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DependentDetailRequest {
    private String name;
    private String relation;
    private LocalDate dob;
}
