package com.moonstack.mapper;

import com.moonstack.dto.request.ExpenseRequest;
import com.moonstack.entity.Expense;
import com.moonstack.enums.PurchaseStatus;
import com.moonstack.enums.RequestStatus;
import com.moonstack.util.UtilsMethods;

public class ExpenseMapper
{
    public static Expense createExpenseRequestToExpenses(ExpenseRequest expenseRequest)
    {
        PurchaseStatus purchaseStatus = PurchaseStatus.fromString(expenseRequest.getPurchaseStatus());

        return Expense.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .invoiceNumber(expenseRequest.getInvoiceNumber())
                .itemName(expenseRequest.getItemName())
                .purchaseDate(expenseRequest.getPurchaseDate())
                .purchaseAmount(expenseRequest.getPurchaseAmount())
                .purchaseStatus(purchaseStatus.getPurchaseStatus())
                .status(RequestStatus.PENDING.getRequestStatus())
                .build();
    }
}
