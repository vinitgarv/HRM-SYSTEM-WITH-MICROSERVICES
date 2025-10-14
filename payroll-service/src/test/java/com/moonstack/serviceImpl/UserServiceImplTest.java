//package com.moonstack.serviceImpl;
//
//import com.moonstack.constants.Message;
//import com.moonstack.dtos.request.ChangePasswordRequest;
//import com.moonstack.dtos.request.UserRequest;
//import com.moonstack.dtos.request.UserUpdateRequest;
//import com.moonstack.dtos.response.UserResponse;
//import com.moonstack.entity.User;
//import com.moonstack.exception.AlreadyPresentException;
//import com.moonstack.exception.NotFoundException;
//import com.moonstack.exception.RequestFailedException;
//import com.moonstack.repository.UserRepository;
//import com.moonstack.response.PageResponse;
//import com.moonstack.service.SessionLogsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private SessionLogsService sessionLoginService;
//
//    private UserRequest userRequest;
//
//    private UserResponse userResponse;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        userRequest = UserRequest.builder()
//                .email("test@mail.com")
//                .firstName("fake")
//                .lastName("user")
//                .build();
//
//        user = User.builder()
//                .id("101")
//                .email("test@mail.com")
//                .firstName("fake")
//                .lastName("user")
//                .acc_type(Message.ACTIVATED)
//                .temp_password("oldPass")
//                .isActive(true)
//                .build();
//    }
//
//
//    @Test
//    void testAdd_success() {
//        when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserResponse response = userService.add(userRequest);
//
//        assertNotNull(response);
//        assertEquals("fake", response.getFirstName());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void testAdd_AlreadyPresent() {
//        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);
//        assertThrows(AlreadyPresentException.class, () -> userService.add(userRequest));
//    }
//
//    @Test
//    void testGetById_Found() {
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//
//        UserResponse response = userService.getById("101");
//
//        assertNotNull(response);
//        assertEquals("fake", response.getFirstName());
//    }
//
//    @Test
//    void testGetById_NotFound() {
//        when(userRepository.findById("101")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> userService.getById("101"));
//    }
//
//    @Test
//    void testGetAll_withPagenation() {
//        Page<User> page = new PageImpl<>(List.of(user));
//        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
//
//        PageResponse<UserResponse> response = userService.getAll(0, 5);
//
//        assertNotNull(response);
//        assertEquals(1, response.getElements());
//        assertEquals(1, response.getTotalPages());
//    }
//
//    @Test
//    void testGetAll_withoutPagenation() {
//        when(userRepository.findAll()).thenReturn(List.of(user));
//
//        PageResponse<UserResponse> response = userService.getAll(null, null);
//
//        assertNotNull(response);
//        assertEquals(1, response.getElements());
//    }
//
//    @Test
//    void testUpdate_Success() {
//        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
//                .firstName("NewName")
//                .lastName("Last")
//                .password("newPass")
//                .build();
//
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserResponse response = userService.update("101", updateRequest);
//        assertNotNull(response);
//        assertEquals("NewName", response.getFirstName());
//    }
//
//    @Test
//    void testUpdate_NotFound() {
//        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
//                .firstName("NewName")
//                .lastName("Last")
//                .password("newPass")
//                .build();
//
//        when(userRepository.findById("101")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> userService.update("101", updateRequest));
//    }
//
//    @Test
//    void testUpdate_NotActivated() {
//        user.setAcc_type(Message.INITIATED);
//
//        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
//                .firstName("NewName")
//                .lastName("Last")
//                .password("newPass")
//                .build();
//
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//        assertThrows(NotFoundException.class, () -> userService.update("101", updateRequest));
//    }
//
//    @Test
//    void testUserActiveInactive_Success() {
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserResponse response = userService.userActiveInactive("101");
//
//        assertNotNull(response);
//        assertFalse(response.getActive());
//    }
//
//    @Test
//    void testUserActiveInactive_NotFound() {
//        when(userRepository.findById("101")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> userService.userActiveInactive("101"));
//    }
//
//    @Test
//    void testDelete_Success() {
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//
//        doNothing().when(userRepository).deleteById("101");
//
//        String result = userService.delete("101");
//        assertEquals(Message.USER + Message.TAB + Message.DELETED + Message.TAB + Message.SUCCESSFULLY + Message.DOT, result);
//    }
//
//    @Test
//    void testDelete_NotFound() {
//        when(userRepository.findById("101")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> userService.delete("101"));
//    }
//
//    @Test
//    void testChangePassword_Success() {
//        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
//                .oldPassword("oldPass")
//                .newPassword("newPass")
//                .confirmPassword("newPass")
//                .build();
//
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        String result = userService.changePassword("101", changePasswordRequest);
//
//        assertEquals(Message.PASSWORD + Message.TAB + Message.CHANGED + Message.TAB + Message.SUCCESSFULLY + Message.DOT, result);
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    void testChangePassword_UserNotFound() {
//        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
//                .oldPassword("oldPass")
//                .newPassword("newPass")
//                .confirmPassword("newPass")
//                .build();
//
//        when(userRepository.findById("101")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> userService.changePassword("101", changePasswordRequest));
//    }
//
//    @Test
//    void testChangePassword_InvalidOldPassword() {
//        ChangePasswordRequest request = new ChangePasswordRequest();
//        request.setOldPassword("wrongPass");
//        request.setNewPassword("newPass");
//        request.setConfirmPassword("newPass");
//
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//
//        assertThrows(RequestFailedException.class, () -> userService.changePassword("101", request));
//    }
//
//    @Test
//    void testChangePassword_NewAndConfirmPasswordMismatch()
//    {
//        ChangePasswordRequest request = new ChangePasswordRequest();
//        request.setOldPassword("oldPass");
//        request.setNewPassword("newPass");
//        request.setConfirmPassword("differentPass");
//
//        when(userRepository.findById("101")).thenReturn(Optional.of(user));
//
//        assertThrows(RequestFailedException.class, () -> userService.changePassword("101", request));
//    }
//
//    @Test
//    void testValidateUser_exist()
//    {
//        String email = "dummy@gmail.com";
//        when(userRepository.existsByEmail(email)).thenReturn(true);
//        Boolean res = userService.validateUser(email);
//
//        assertEquals(true,res);
//    }
//
//    @Test
//    void testValidateUser_notExist()
//    {
//        String email = "dummy@gmail.com";
//        when(userRepository.existsByEmail(email)).thenReturn(false);
//        Boolean res = userService.validateUser(email);
//
//        assertEquals(false,res);
//    }
//}
