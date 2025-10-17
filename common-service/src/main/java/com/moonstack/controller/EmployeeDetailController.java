package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.request.RejectDocumentRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.response.ApiResponse;
import com.moonstack.service.EmployeeDetailsService;
import com.moonstack.service.IdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeDetailController {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Autowired
    private IdentityInfoService identityInfoService;

    // TODO Remove add from the endpoint
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> add(@RequestBody EmployeeDetailsRequest request, @PathVariable String userId)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.addEmployeeDetails(request,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> update(@RequestBody EmployeeDetailsRequest request, @PathVariable String userId)
    {
        ApiResponse<EmployeeDetailsResponse> response = ApiResponse.<EmployeeDetailsResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.update(userId,request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{userId}")

    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getById( @PathVariable String userId)
    {
        ApiResponse<EmployeeDetailsResponse> response = ApiResponse.<EmployeeDetailsResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.getById(userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
//
//    @PostMapping(value = "/upload-document/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadDocument(
//            @RequestPart("file") MultipartFile file,
//            @PathVariable String userId,
//            @RequestBody String type) {
//
//        FileUploadResponse fileResponse = employeeDetailsService.uploadDocument(file, userId, type);
//
//        ApiResponse<FileUploadResponse> response = ApiResponse.<FileUploadResponse>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("File uploaded successfully")
//                .multiple(false)
//                .data(fileResponse)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping(value = "/upload-document/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @PathVariable String userId,
            @RequestPart("body") Map<String, String> body) {

        String type = body.get("type");

        FileUploadResponse fileResponse = employeeDetailsService.uploadDocument(file, userId, type);

        ApiResponse<FileUploadResponse> response = ApiResponse.<FileUploadResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("File uploaded successfully")
                .multiple(false)
                .data(fileResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/verify/{documentId}")
    public ResponseEntity<ApiResponse<String>> verifyDocument(@PathVariable String documentId)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(identityInfoService.verifyDocument(documentId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reject/{documentId}")
    public ResponseEntity<ApiResponse<String>> rejectDocument(@RequestBody RejectDocumentRequest request, @PathVariable String documentId)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(identityInfoService.rejectDocument(documentId,request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<String>> deleteDocument(
            @PathVariable String employeeId,
            @RequestParam String fileName) {

        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.deleteDocument(employeeId, fileName))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
