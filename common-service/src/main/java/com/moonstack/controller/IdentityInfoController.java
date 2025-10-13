package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.RejectDocumentRequest;
import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.response.ApiResponse;
import com.moonstack.service.IdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class IdentityInfoController {

    @Autowired
    private IdentityInfoService identityInfoService;


    // TODO make it for authenticated
    @PostMapping(value = "/upload-identity/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadIdentityFile(
            @RequestPart("file") MultipartFile file,
            @PathVariable String userId,
            @RequestParam("type") String type) {

        FileUploadResponse fileResponse = identityInfoService.uploadIdentityInfoFile(file, userId, type);

        ApiResponse<FileUploadResponse> response = ApiResponse.<FileUploadResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("File uploaded successfully")
                .multiple(false)
                .data(fileResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // TODO Add both api's for admin role
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
    public ResponseEntity<ApiResponse<String>> rejectDocument(@PathVariable String documentId, @RequestBody RejectDocumentRequest request)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(identityInfoService.rejectDocument(documentId,request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
