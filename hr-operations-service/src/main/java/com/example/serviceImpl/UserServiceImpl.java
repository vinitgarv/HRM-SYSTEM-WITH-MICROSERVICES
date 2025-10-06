//package com.example.serviceImpl;
//
//import com.example.constants.Message;
//import com.example.dto.request.RegisterRequest;
//import com.example.dto.response.PageResponse;
//import com.example.dto.response.UserResponse;
//import com.example.entity.User;
//import com.example.exception.AlreadyPresentException;
//import com.example.exception.InvalidSessionException;
//import com.example.exception.NotFoundException;
//import com.example.mapper.UserMapper;
//import com.example.repository.UserRepository;
//import com.example.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public PageResponse<UserResponse> getAll(Integer page, Integer size) {
//        List<UserResponse> responseList;
//        int totalPages = 0;
//        int totalElements = 0;
//        if (page != null && size != null) {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<User> userPage = userRepository.findAll(pageable);
//            responseList = userPage.get().map(UserMapper::convertUsertoUserResponse).toList();
//            totalElements = (int) userPage.getTotalElements();
//            totalPages = userPage.getTotalPages();
//        } else {
//            responseList = userRepository.findAll().stream().map(UserMapper::convertUsertoUserResponse).toList();
//            totalElements = responseList.size();
//        }
//        return PageResponse.<UserResponse>builder()
//                .data(responseList)
//                .elements(totalElements)
//                .totalPages(totalPages)
//                .pageNo(page)
//                .limit(size)
//                .build();
//    }
//
//    @Override
//    public User getById(String id, String username) {
//        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(Message.USER + Message.TAB + Message.NOT_FOUND + Message.DOT));
//        if (!user.getEmail().equalsIgnoreCase(username))
//            throw new InvalidSessionException(Message.INVALID_SESSION);
//        return user;
//    }
//
//    @Override
//    public User update(String id, RegisterRequest registerRequest) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(Message.USER+Message.TAB+Message.NOT_FOUND+Message.DOT));
//
//        Optional<User> optionalUser =  userRepository.findByEmail(registerRequest.getEmail());
//
//        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(id))
//            throw new AlreadyPresentException(Message.EMAIL+Message.ALREADY+Message.PRESENT);
//
//        existingUser.setEmail(registerRequest.getEmail());
//        existingUser.setPassword(existingUser.getPassword());
//        return null;
//    }
//
//    @Override
//    public User findById(String id) {
//        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
//    }
//}
