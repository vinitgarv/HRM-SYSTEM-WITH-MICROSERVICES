package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.User;
import com.moonstack.exception.AlreadyPresentException;
import com.moonstack.exception.InvalidSessionException;
import com.moonstack.exception.NotFoundException;
import com.moonstack.mapper.UserMapper;
import com.moonstack.repository.UserRepository;
import com.moonstack.response.PageResponse;
import com.moonstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageResponse<UserResponse> getAll(Integer page, Integer size) {
        List<UserResponse> responseList;
        int totalPages = 0;
        int totalElements = 0;
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAll(pageable);
            responseList = userPage.get().map(UserMapper::convertUsertoUserResponse).toList();
            totalElements = (int) userPage.getTotalElements();
            totalPages = userPage.getTotalPages();
        } else {
            responseList = userRepository.findAll().stream().map(UserMapper::convertUsertoUserResponse).toList();
            totalElements = responseList.size();
        }
        return PageResponse.<UserResponse>builder()
                .data(responseList)
                .elements(totalElements)
                .totalPages(totalPages)
                .pageNo(page)
                .limit(size)
                .build();
    }

    @Override
    public User getById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.USER + Message.TAB +
                                                         Message.NOT_FOUND + Message.DOT));
        return user;
    }

    @Override
    public User update(String id, RegisterRequest registerRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.USER+Message.TAB+Message.NOT_FOUND+Message.DOT));

        Optional<User> optionalUser =  userRepository.findByEmail(registerRequest.getEmail());

        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(id))
            throw new AlreadyPresentException(Message.EMAIL+Message.ALREADY+Message.PRESENT);

        existingUser.setEmail(registerRequest.getEmail());
        existingUser.setPassword(existingUser.getPassword());
        return null;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void add(User user) {
userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAll() {

        List<User> users  = userRepository.findAll();
        if (users.isEmpty())
            throw new NotFoundException("Users not found");

        return users.stream().map(UserMapper::convertUsertoUserResponse).toList();
    }
}
