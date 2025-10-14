package com.moonstack.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllExpensesResponse {
    private List<ExpenseResponse> expenseResponses;
    private int totalExpense;
    private int totalPaid;
    private int totalUnpaid;
    private int totalReturn;
}