package com.moonstack.dto.request;

import com.moonstack.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseRequest {
    private String invoiceNumber;
    private String itemName;
    private String purchasedBy;
    private LocalDate purchaseDate;
    private int purchaseAmount;
    private PurchaseStatus purchaseStatus;
}