package com.moonstack.dto.request;

import com.moonstack.enums.PurchaseStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private String invoiceNumber;
    private String itemName;
    private String purchasedBy;
    private LocalDate purchaseDate;
    private int purchaseAmount;
    private String purchaseStatus;
}