package com.moonstack.dto.response;

import com.moonstack.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseResponse {
    private String id;
    private String invoiceNumber;
    private String itemName;
    private String purchasedBy;
    private LocalDate purchaseDate;
    private int purchaseAmount;
    private String purchaseStatus;
    private String approvedBy;
    private LocalDate approvedDate;
    private String invoiceFileName;
    private String fileType;
    private String status;
}
