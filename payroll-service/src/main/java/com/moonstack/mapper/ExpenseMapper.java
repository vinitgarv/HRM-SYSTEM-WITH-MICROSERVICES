package com.moonstack.mapper;

import com.moonstack.dto.request.ExpenseRequest;
import com.moonstack.entity.Expense;
import com.moonstack.enums.RequestStatus;
import com.moonstack.util.UtilsMethods;

public class ExpenseMapper
{
    public static Expense createExpenseRequestToExpenses(ExpenseRequest expenseRequest)
    {
        return Expense.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .invoiceNumber(expenseRequest.getInvoiceNumber())
                .itemName(expenseRequest.getItemName())
                .purchaseDate(expenseRequest.getPurchaseDate())
                .purchaseAmount(expenseRequest.getPurchaseAmount())
                .purchaseStatus(expenseRequest.getPurchaseStatus())
                .status(RequestStatus.PENDING)
                .build();
    }
}
