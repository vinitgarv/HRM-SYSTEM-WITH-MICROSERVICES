package com.moonstack.controller;

import com.moonstack.apiResponse.ApiResponse;
import com.moonstack.constant.Message;
import com.moonstack.dto.request.ExpenseRequest;
import com.moonstack.dto.response.AllExpensesResponse;
import com.moonstack.dto.response.ExpenseResponse;
import com.moonstack.dto.response.FileResource;
import com.moonstack.enums.PurchaseStatus;
import com.moonstack.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/expense")
public class ExpenseController
{
    @Autowired
    private ExpenseService expensesService;

    @PostMapping(value = "/create-expense-request", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<String>> createExpenseRequest(
            @RequestPart("data") ExpenseRequest expensesRequest,
            @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {

        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("SUCCESS")
                .multiple(false)
                .data(expensesService.createExpenseRequest(expensesRequest, file))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/approve/{expenseRequestId}")
    public ResponseEntity<ApiResponse<String>> approveExpenseRequest(@PathVariable String expenseRequestId) {
        expensesService.approveExpenseRequest(expenseRequestId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Expense Request Approved Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reject/{expenseRequestId}")
    public ResponseEntity<ApiResponse<String>> rejectExpenseRequest(@PathVariable String expenseRequestId) {
        expensesService.rejectExpenseRequest(expenseRequestId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Expense Request Rejected Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/find-expense-request/{expenseRequestId}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getLeaveById(@PathVariable String expenseRequestId) {
        ApiResponse<ExpenseResponse> response = ApiResponse.<ExpenseResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(expensesService.getExpenseRequestById(expenseRequestId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AllExpensesResponse>> getAllExpensesRequest() {
        ApiResponse<AllExpensesResponse> response = ApiResponse.<AllExpensesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(expensesService.getAllExpenseRequests())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{expenseRequestId}")
    public ResponseEntity<ApiResponse<String>> deleteExpenseRequest(@PathVariable String expenseRequestId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(expensesService.deleteExpenseRequestById(expenseRequestId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{expenseRequestId}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> updateExpenseRequest(@PathVariable String expenseRequestId, @RequestBody ExpenseRequest updateRequest) {
        ApiResponse<ExpenseResponse> response = ApiResponse.<ExpenseResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(expensesService.updateExpenseRequest(expenseRequestId,updateRequest))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/user-expenses/{userId}")
    public ResponseEntity<ApiResponse<AllExpensesResponse>> getAllExpensesRequestByUser(@PathVariable String userId) {
        ApiResponse<AllExpensesResponse> response = ApiResponse.<AllExpensesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(expensesService.allExpenseRequestByUser(userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<AllExpensesResponse>> filterExpenses(@RequestParam(required = false) String purchaseStatus){
        ApiResponse<AllExpensesResponse>response = ApiResponse.<AllExpensesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(expensesService.filterExpensesByPurchaseStatus(purchaseStatus))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> previewFile(@PathVariable String fileName) {
        try {
            FileResource fileResource = expensesService.getInvoiceByFileName(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileResource.getContentType()));
            headers.setContentDispositionFormData("inline", fileResource.getResource().getFilename());
            return new ResponseEntity<>(fileResource.getResource(), headers, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/get-invoice/{expenseId}")
    public ResponseEntity<Resource> previewInvoice(@PathVariable String expenseId) {
        try {
            FileResource fileResource = expensesService.getInvoiceByExpenseId(expenseId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileResource.getContentType()));
            headers.setContentDispositionFormData("inline", fileResource.getResource().getFilename());
            return new ResponseEntity<>(fileResource.getResource(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
