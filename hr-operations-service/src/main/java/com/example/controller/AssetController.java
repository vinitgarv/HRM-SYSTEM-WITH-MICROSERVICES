package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.AssetRequest;
import com.example.dto.request.AssetUpdateRequest;
import com.example.dto.response.AssetResponse;
import com.example.dto.response.PageResponse;
import com.example.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController
{
    @Autowired
    private AssetService assetService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AssetResponse>> add(@RequestBody AssetRequest assetRequest) {
        ApiResponse<AssetResponse> response = ApiResponse.<AssetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.add(assetRequest))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetResponse>> getById(@PathVariable("id") String id) {
        ApiResponse<AssetResponse> response = ApiResponse.<AssetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.getById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> getAll() {
        ApiResponse<List<AssetResponse>> response = ApiResponse.<List<AssetResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.getAll())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageResponse<AssetResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<AssetResponse>> response = ApiResponse.<PageResponse<AssetResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.getAllWithPagination(page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AssetResponse>> update(@PathVariable("id") String id,@RequestBody AssetUpdateRequest assetRequest) {
        ApiResponse<AssetResponse> response = ApiResponse.<AssetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.update(id,assetRequest))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(assetService.delete(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
