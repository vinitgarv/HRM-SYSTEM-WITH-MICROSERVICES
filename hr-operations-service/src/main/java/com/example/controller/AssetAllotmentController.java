package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.response.AssetAllotmentResponse;
import com.example.dto.response.PageResponse;
import com.example.service.AssetAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset-allotment")
public class AssetAllotmentController
{
    @Autowired
    private AssetAllotmentService assetAllotmentService;

    @PostMapping("/{assetId}/{userId}")
    public ResponseEntity<ApiResponse<AssetAllotmentResponse>> allot(@PathVariable("assetId") String assetId,
                                                                   @PathVariable("userId") String userId)
    {
        ApiResponse<AssetAllotmentResponse> response = ApiResponse.<AssetAllotmentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.allotAsset(assetId,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{allotId}/{assetId}/{userId}")
    public ResponseEntity<ApiResponse<AssetAllotmentResponse>> update(@PathVariable("allotId") String allotId,
                                                                   @PathVariable("assetId") String assetId,
                                                                   @PathVariable("userId") String userId)
    {
        ApiResponse<AssetAllotmentResponse> response = ApiResponse.<AssetAllotmentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.update(allotId,assetId,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetAllotmentResponse>> getById(@PathVariable("id") String id) {
        ApiResponse<AssetAllotmentResponse> response = ApiResponse.<AssetAllotmentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.getById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AssetAllotmentResponse>>> getAll() {
        ApiResponse<List<AssetAllotmentResponse>> response = ApiResponse.<List<AssetAllotmentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.getAll())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageResponse<AssetAllotmentResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<AssetAllotmentResponse>> response = ApiResponse.<PageResponse<AssetAllotmentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.getAllWithPagination(page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetAllotmentService.delete(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
