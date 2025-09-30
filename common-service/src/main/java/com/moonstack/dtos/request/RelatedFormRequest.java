package com.moonstack.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedFormRequest
{
    private String type;   // Asset, Benefit, TravelExpense, TravelRequest
    private String details;
}
