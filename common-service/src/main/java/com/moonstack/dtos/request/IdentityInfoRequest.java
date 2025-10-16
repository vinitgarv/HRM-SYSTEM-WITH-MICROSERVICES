package com.moonstack.dtos.request;

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

}
