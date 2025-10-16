package com.moonstack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityInfoResponse
{
    private String id;
    private String documentType;
    private String documentNumber;
    private String status;
    private String fileName;
    private String fileType;
    private String remark;
}
