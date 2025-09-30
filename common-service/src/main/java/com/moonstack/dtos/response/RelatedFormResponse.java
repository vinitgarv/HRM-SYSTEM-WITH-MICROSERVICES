package com.moonstack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedFormResponse
{
    private String type;   // Asset, Benefit, TravelExpense, TravelRequest
    private String details;
}
