package com.moonstack.service;

import com.moonstack.dto.request.ExpenseRequest;
import com.moonstack.dto.response.AllExpensesResponse;
import com.moonstack.dto.response.ExpenseResponse;
import com.moonstack.dto.response.FileResource;
import com.moonstack.entity.Expense;
import com.moonstack.enums.PurchaseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExpenseService
{
    String createExpenseRequest(ExpenseRequest expensesRequest, MultipartFile file) throws IOException;

    Expense approveExpenseRequest(String expenseRequestId);

    Expense rejectExpenseRequest(String expenseRequestId);

    ExpenseResponse getExpenseRequestById(String expenseRequestId);

    AllExpensesResponse getAllExpenseRequests();

    String deleteExpenseRequestById(String expenseRequestId);

    ExpenseResponse updateExpenseRequest(String expenseRequestId,ExpenseRequest updateRequest);

    AllExpensesResponse allExpenseRequestByUser(String userId);

    AllExpensesResponse filterExpensesByPurchaseStatus(PurchaseStatus purchaseStatus);

    FileResource getInvoiceByFileName(String fileName) throws Exception;

    FileResource getInvoiceByExpenseId(String expenseRequestId) throws Exception;
}
