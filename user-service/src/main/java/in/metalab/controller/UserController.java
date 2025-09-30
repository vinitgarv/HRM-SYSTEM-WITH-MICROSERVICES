package in.metalab.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.metalab.constants.UserApiConstants;
import in.metalab.constants.UserCommonMessages;
import in.metalab.dto.ApiResponse;
import in.metalab.dto.UserRequest;
import in.metalab.dto.UserResponse;
import in.metalab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = UserApiConstants.USER_REQUEST_MAPPING)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "This api is used to register new users.")
    @PostMapping(path = UserApiConstants.REGISTER_USER)
    public ResponseEntity<?> register(@RequestBody UserRequest user) {
        log.info("Request received into register api with {} ", user);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.CREATED.name(), String.valueOf(HttpStatus.CREATED.value()), userService.registerUser(user)), HttpStatus.CREATED);
    }

    @Operation(summary = "This api is used to login users.")
    @GetMapping(path = UserApiConstants.LOGIN_USER)
    public ResponseEntity<?> login(@RequestParam @Valid String emailOrMobileNo, @RequestParam @Valid String password) {
        log.info("Request recieved into login api with {} ", emailOrMobileNo);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), userService.loginUser(emailOrMobileNo, password)), HttpStatus.OK);
    }

    @Operation(summary = "This api is used to check user is new or old.")
    @GetMapping(path = UserApiConstants.CHECK_USER_BY + "/email")
    public ResponseEntity<?> checkEmailExists(@RequestParam @Valid String email) {
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), userService.existsByEmail(email)), HttpStatus.OK);
    }

    @Operation(summary = "This api is used to check user is new or old.")
    @GetMapping(path = UserApiConstants.CHECK_USER_BY + "/mobileNo")
    public ResponseEntity<?> checkMobileNoExists(@RequestParam @Valid String mobileNo) {
        log.info("Request recieved into login api with {} ", mobileNo);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), userService.existsByMobileNo(mobileNo)), HttpStatus.OK);
    }

    @Operation(summary = "This api is used to get user by provided id.")
    @GetMapping(path = UserApiConstants.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(@RequestParam("userId") @Valid String id) {
        log.info("Request received into user get by id api with {} ", id);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), userService.getUserById(id)), HttpStatus.OK);
    }

    @Operation(summary = "This api is used to delete user by provided id.")
    @DeleteMapping(path = UserApiConstants.DELETE_USER_BY_ID)
    public ResponseEntity<?> deleteUserById(@RequestParam("userId") @Valid String id) {
        log.info("Request recieved into delete user by id api with {} ", id);
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), UserCommonMessages.DELETED_SUCCESSFULLY), HttpStatus.OK);
    }

    @Operation(summary = "This api is used to inactive user by provided id.")
    @PatchMapping(path = UserApiConstants.INACTIVATE_USER_BY_ID)
    public ResponseEntity<?> inactiveUserById(@RequestParam("userId") @Valid String id) {
        log.info("Request recieved into delete user by id api with {} ", id);
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.SUCCESS, HttpStatus.OK.name(), String.valueOf(HttpStatus.OK.value()), userService.activeInactiveUserById(id)), HttpStatus.OK);
    }
}
