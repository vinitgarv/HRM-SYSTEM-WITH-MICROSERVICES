package com.moonstack.serviceImpl;


import com.moonstack.apiResponse.ApiResponse;
import com.moonstack.client.UserClient;
import com.moonstack.dto.request.ExpenseRequest;
import com.moonstack.dto.response.AllExpensesResponse;
import com.moonstack.dto.response.ExpenseResponse;
import com.moonstack.dto.response.FileResource;
import com.moonstack.dto.response.UserResponse;
import com.moonstack.entity.Expense;
import com.moonstack.enums.PurchaseStatus;
import com.moonstack.enums.RequestStatus;
import com.moonstack.exception.NotFoundException;
import com.moonstack.mapper.ExpenseMapper;
import com.moonstack.repository.ExpenseRepository;
import com.moonstack.service.ExpenseService;
import com.moonstack.util.ExpenseSpecification;
import com.moonstack.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String FILE_DIR = "E:\\Aditya Task\\HRM-SYSTEM-WITH-MICROSERVICES\\uploads";

    @Override
    public String createExpenseRequest(ExpenseRequest expensesRequest, MultipartFile file) throws IOException
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(expensesRequest.getPurchasedBy());
        UserResponse user = apiResponse.getData();

        Expense expense = ExpenseMapper.createExpenseRequestToExpenses(expensesRequest);
        expense.setPurchasedBy(user.getFirstName()+" "+user.getLastName());
        expense.setUser(user.getId());
        if (file != null && !file.isEmpty()) {
            String uploadDir = new File("uploads").getAbsolutePath();
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String fileName = user.getFirstName()+"_"+ UUID.randomUUID().toString().substring(0, 4)+"_"+ file.getOriginalFilename();
            File dest = new File(uploadFolder, fileName);
            file.transferTo(dest);

            expense.setInvoiceFileName(fileName);

            String extension = "";
            if (fileName != null && fileName.contains(".")) {
                extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            expense.setFileType(extension.toLowerCase());
        }

        repository.save(expense);
        return "Expense Request Created Successfully";
    }

    @Override
    public Expense approveExpenseRequest(String expenseRequestId)
    {
        String token  = jwtUtil.extractToken(servletRequest);
        String userId = jwtUtil.extractUserId(token);

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        Expense expense = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));

        expense.setStatus(RequestStatus.APPROVED.getRequestStatus());
        expense.setApprovedBy(user.getFirstName()+" "+user.getLastName());
        expense.setApprovedDate(LocalDate.now());
        repository.save(expense);
        return expense;
    }

    @Override
    public Expense rejectExpenseRequest(String expenseRequestId)
    {
        Expense expenses = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));

        String token  = jwtUtil.extractToken(servletRequest);
        String userId = jwtUtil.extractUserId(token);

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        expenses.setStatus(RequestStatus.REJECTED.getRequestStatus());
        expenses.setApprovedBy(user.getFirstName()+" "+user.getLastName());
        expenses.setApprovedDate(LocalDate.now());
        repository.save(expenses);
        return expenses;
    }

    @Override
    public ExpenseResponse getExpenseRequestById(String expenseRequestId) {
        Expense expenses = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));

        PurchaseStatus purchaseStatus = PurchaseStatus.fromString(expenses.getPurchaseStatus());

        return ExpenseResponse.builder()
                .id(expenses.getId())
                .invoiceNumber(expenses.getInvoiceNumber())
                .itemName(expenses.getItemName())
                .purchasedBy(expenses.getPurchasedBy())
                .purchaseDate(expenses.getPurchaseDate())
                .purchaseAmount(expenses.getPurchaseAmount())
                .purchaseStatus(purchaseStatus.getPurchaseStatus())
                .approvedBy(expenses.getApprovedBy())
                .approvedDate(expenses.getApprovedDate())
                .invoiceFileName(expenses.getInvoiceFileName())
                .fileType(expenses.getFileType())
                .status(expenses.getStatus())
                .build();
    }

    @Override
    public AllExpensesResponse getAllExpenseRequests() {

        List<Expense> expenses  = repository.findAll();
        if(expenses.isEmpty()) {
            throw new NotFoundException("No Expense Request Found");
        }

        List<ExpenseResponse> expenseResponses = expenses.stream().map(expense -> ExpenseResponse.builder()
                .id(expense.getId())
                .invoiceNumber(expense.getInvoiceNumber())
                .itemName(expense.getItemName())
                .purchasedBy(expense.getPurchasedBy())
                .purchaseDate(expense.getPurchaseDate())
                .purchaseAmount(expense.getPurchaseAmount())
                .purchaseStatus(expense.getPurchaseStatus())
                .approvedDate(expense.getApprovedDate())
                .approvedBy(expense.getApprovedBy())
                .invoiceFileName(expense.getInvoiceFileName())
                .fileType(expense.getFileType())
                .status(expense.getStatus())
                .build()).toList();

        int totalExpense = expenseResponses.stream()
                .mapToInt(ExpenseResponse::getPurchaseAmount).sum();

        int totalPaid = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.PAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();

        int totalUnpaid = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.UNPAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();

        int totalReturned = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.RETURNED.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();

        return AllExpensesResponse.builder()
                .expenseResponses(expenseResponses)
                .totalExpense(totalExpense)
                .totalPaid(totalPaid)
                .totalUnpaid(totalUnpaid)
                .totalReturn(totalReturned)
                .build();
    }

    @Override
    public String deleteExpenseRequestById(String expenseRequestId) {
        Expense expenses = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));
        repository.deleteById(expenseRequestId);
        return "Expense Request Deleted Successfully";
    }

    @Override
    public ExpenseResponse updateExpenseRequest(String expenseRequestId, ExpenseRequest updateRequest)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(updateRequest.getPurchasedBy());
        UserResponse user = apiResponse.getData();

        Expense expense = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));

        expense.setInvoiceNumber(updateRequest.getInvoiceNumber());
        expense.setItemName(updateRequest.getItemName());
        expense.setPurchasedBy(user.getFirstName());
        expense.setPurchaseDate(updateRequest.getPurchaseDate());
        expense.setPurchaseAmount(updateRequest.getPurchaseAmount());
        expense.setPurchaseStatus(updateRequest.getPurchaseStatus());
        expense.setUser(user.getId());
        expense.setStatus(RequestStatus.PENDING.getRequestStatus());
        expense.setApprovedBy(null);
        expense.setApprovedDate(null);
        Expense updated = repository.save(expense);

        return ExpenseResponse.builder()
                .id(updated.getId())
                .invoiceNumber(updated.getInvoiceNumber())
                .itemName(updated.getItemName())
                .purchasedBy(updated.getPurchasedBy())
                .purchaseDate(updated.getPurchaseDate())
                .purchaseAmount(updated.getPurchaseAmount())
                .purchaseStatus(updated.getPurchaseStatus())
                .approvedBy(updated.getApprovedBy())
                .approvedDate(updated.getApprovedDate())
                .invoiceFileName(updated.getInvoiceFileName())
                .fileType(updated.getFileType())
                .build();
    }

    @Override
    public AllExpensesResponse allExpenseRequestByUser(String userId)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        List<Expense> expenses = repository.findAll();

        List<ExpenseResponse> userExpenses = expenses.stream()
                .filter(expense -> expense.getUser().equals(userId))
                .map(expense -> ExpenseResponse.builder()
                        .id(expense.getId())
                        .invoiceNumber(expense.getInvoiceNumber())
                        .itemName(expense.getItemName())
                        .purchasedBy(expense.getPurchasedBy())
                        .purchaseDate(expense.getPurchaseDate())
                        .purchaseAmount(expense.getPurchaseAmount())
                        .purchaseStatus(expense.getPurchaseStatus())
                        .approvedDate(expense.getApprovedDate())
                        .approvedBy(expense.getApprovedBy())
                        .invoiceFileName(expense.getInvoiceFileName())
                        .fileType(expense.getFileType())
                        .status(expense.getStatus())
                        .build())
                .toList();

        if (userExpenses.isEmpty()) {
            throw new NotFoundException("No Expense Request Found for the User");
        }

        int totalExpense = userExpenses.stream().mapToInt(ExpenseResponse::getPurchaseAmount).sum();
        int totalPaid = userExpenses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.PAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();
        int totalUnpaid = userExpenses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.UNPAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();
        int totalReturned = userExpenses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.RETURNED.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();

        return AllExpensesResponse.builder()
                .expenseResponses(userExpenses)
                .totalExpense(totalExpense)
                .totalPaid(totalPaid)
                .totalUnpaid(totalUnpaid)
                .totalReturn(totalReturned)
                .build();
    }

    @Override
    public AllExpensesResponse filterExpensesByPurchaseStatus(String purchaseStatus) {
        Specification<Expense> spec = Specification.where(ExpenseSpecification.hasStatus(purchaseStatus));

        List<ExpenseResponse> expenseResponses =  repository.findAll(spec).stream()
                .map(expense -> ExpenseResponse.builder()
                        .id(expense.getId())
                        .invoiceNumber(expense.getInvoiceNumber())
                        .itemName(expense.getItemName())
                        .purchasedBy(expense.getPurchasedBy())
                        .purchaseDate(expense.getPurchaseDate())
                        .purchaseAmount(expense.getPurchaseAmount())
                        .purchaseStatus(expense.getPurchaseStatus())
                        .approvedDate(expense.getApprovedDate())
                        .approvedBy(expense.getApprovedBy())
                        .invoiceFileName(expense.getInvoiceFileName())
                        .fileType(expense.getFileType())
                        .status(expense.getStatus())
                        .build()).toList();

        int totalExpense = expenseResponses.stream()
                .mapToInt(ExpenseResponse::getPurchaseAmount).sum();

        int totalPaid = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.PAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();
        int totalUnpaid = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.UNPAID.getPurchaseStatus()))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();
        int totalReturned = expenseResponses.stream()
                .filter(e -> e.getPurchaseStatus().equals(PurchaseStatus.RETURNED))
                .mapToInt(ExpenseResponse::getPurchaseAmount)
                .sum();

        return AllExpensesResponse.builder()
                .expenseResponses(expenseResponses)
                .totalExpense(totalExpense)
                .totalPaid(totalPaid)
                .totalUnpaid(totalUnpaid)
                .totalReturn(totalReturned)
                .build();
    }

    @Override
    public FileResource getInvoiceByFileName(String fileName) throws Exception {
        Path filePath = Paths.get(FILE_DIR).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new Exception("File not found: " + fileName);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return FileResource.builder()
                .resource(resource)
                .contentType(contentType)
                .build();
    }

    @Override
    public FileResource getInvoiceByExpenseId(String expenseRequestId) throws Exception{

        Expense expense = repository.findById(expenseRequestId)
                .orElseThrow(() -> new RuntimeException("Expense Request Not Found"));


        String fileName = expense.getInvoiceFileName();
        if (fileName == null || fileName.isBlank()) {
            throw new Exception("No invoice uploaded for expenseId: " + expenseRequestId);
        }

        Path filePath = Paths.get(FILE_DIR).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new Exception("File not found on disk: " + fileName);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return FileResource.builder()
                .resource(resource)
                .contentType(contentType)
                .build();
    }
}
