package com.moonstack.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityInfoRequest
{
    private String uan;
    private String pan;
    private String aadhar;
}
