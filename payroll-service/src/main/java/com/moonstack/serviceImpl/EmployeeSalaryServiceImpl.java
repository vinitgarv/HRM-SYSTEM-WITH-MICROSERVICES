package com.moonstack.serviceImpl;

import com.moonstack.apiResponse.ApiResponse;
import com.moonstack.client.UserClient;
import com.moonstack.dto.request.EmployeeSalaryRequest;
import com.moonstack.dto.response.EmployeeSalaryResponse;
import com.moonstack.dto.response.RoleResponse;
import com.moonstack.dto.response.UserResponse;
import com.moonstack.entity.EmployeeSalary;
import com.moonstack.exception.AlreadyPresentException;
import com.moonstack.exception.ForbiddenException;
import com.moonstack.exception.NotFoundException;
import com.moonstack.mapper.EmployeeSalaryMapper;
import com.moonstack.repository.EmployeeSalaryRepository;
import com.moonstack.service.EmployeeSalaryService;
import com.moonstack.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService
{

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    public EmployeeSalaryResponse add(EmployeeSalaryRequest request, String userId) {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse userResponse = apiResponse.getData();

        String token = jwtTokenUtil.extractToken(servletRequest);
        List<String> tokenRoles = jwtTokenUtil.extractRoles(token);

        // Normalize token roles (uppercase + remove ROLE_)
        Set<String> normalizedTokenRoles = tokenRoles.stream()
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        // Normalize user roles (uppercase + remove ROLE_ + replace spaces)
        Set<String> userRoles = userResponse.getRoles().stream()
                .map(RoleResponse::getName)
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        System.out.println("Token roles: " + normalizedTokenRoles);
        System.out.println("User roles: " + userRoles);

        if (normalizedTokenRoles.contains("SUPER_ADMIN"))
        {
            // SUPER_ADMIN can add salary for anyone
        }
        else if (normalizedTokenRoles.contains("ADMIN"))
        {
            if (userRoles.contains("SUPER_ADMIN"))
            {
                throw new ForbiddenException("ADMIN cannot add salary for SUPER_ADMIN users.");
            }
        }
        else
        {
            throw new ForbiddenException("You are not authorized to add salary records.");
        }

        if (employeeSalaryRepository.existsByUserId(userId))
            throw new AlreadyPresentException("Salary data already present");

        request.validate();

        EmployeeSalary employeeSalary = EmployeeSalaryMapper.convertEmployeeSalaryRequestToEmployeeSalary(request);
        employeeSalary.setUserId(userId);
        employeeSalaryRepository.save(employeeSalary);

        EmployeeSalaryResponse response = EmployeeSalaryMapper.convertEmployeeSalaryToEmployeeSalaryResponse(employeeSalary);
        response.setUsername(userResponse.getFirstName() + " " + userResponse.getLastName());

        return response;
    }

    @Override
    public EmployeeSalaryResponse update(EmployeeSalaryRequest request, String salaryId)
    {
        EmployeeSalary existing = employeeSalaryRepository.findById(salaryId)
                .orElseThrow(() -> new NotFoundException("No data found"));

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(existing.getUserId());
        UserResponse userResponse = apiResponse.getData();

        String token = jwtTokenUtil.extractToken(servletRequest);
        List<String> tokenRoles = jwtTokenUtil.extractRoles(token);

        // Normalize token roles (uppercase + remove ROLE_)
        Set<String> normalizedTokenRoles = tokenRoles.stream()
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        // Normalize user roles (uppercase + remove ROLE_ + replace spaces)
        Set<String> userRoles = userResponse.getRoles().stream()
                .map(RoleResponse::getName)
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        System.out.println("Token roles: " + normalizedTokenRoles);
        System.out.println("User roles: " + userRoles);

        if (normalizedTokenRoles.contains("SUPER_ADMIN"))
        {
            // SUPER_ADMIN can add salary for anyone
        }
        else if (normalizedTokenRoles.contains("ADMIN"))
        {
            if (userRoles.contains("SUPER_ADMIN"))
            {
                throw new ForbiddenException("ADMIN cannot add salary for SUPER_ADMIN users.");
            }
        }
        else
        {
            throw new ForbiddenException("You are not authorized to add salary records.");
        }

        request.validate();

        Double netSal = request.getBaseSalary() + request.getAllowances() - request.getDeductions() - request.getTaxAmounts();
        existing.setNetSalary(netSal);
        existing.setBaseSalary(request.getBaseSalary());
        existing.setAllowances(request.getAllowances());
        existing.setDeductions(request.getDeductions());
        existing.setTaxAmounts(request.getTaxAmounts());
        existing.setBankName(request.getBankName());
        existing.setAccountNumber(request.getAccountNumber());
        employeeSalaryRepository.save(existing);

        EmployeeSalaryResponse response = EmployeeSalaryMapper.convertEmployeeSalaryToEmployeeSalaryResponse(existing);
        response.setUsername(userResponse.getFirstName() + " " + userResponse.getLastName());
        return response;
    }

    @Override
    public EmployeeSalaryResponse getById(String userId)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse userResponse = apiResponse.getData();

        String token = jwtTokenUtil.extractToken(servletRequest);
        List<String> tokenRoles = jwtTokenUtil.extractRoles(token);

        // Normalize token roles (uppercase + remove ROLE_)
        Set<String> normalizedTokenRoles = tokenRoles.stream()
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        // Normalize user roles (uppercase + remove ROLE_ + replace spaces)
        Set<String> userRoles = userResponse.getRoles().stream()
                .map(RoleResponse::getName)
                .map(role -> role.replace("ROLE_", "").toUpperCase().replace(" ", "_"))
                .collect(Collectors.toSet());

        System.out.println("Token roles: " + normalizedTokenRoles);
        System.out.println("User roles: " + userRoles);

        if (normalizedTokenRoles.contains("SUPER_ADMIN"))
        {
            // SUPER_ADMIN can add salary for anyone
        }
        else if (normalizedTokenRoles.contains("ADMIN"))
        {
            if (userRoles.contains("SUPER_ADMIN"))
            {
                throw new ForbiddenException("ADMIN cannot add salary for SUPER_ADMIN users.");
            }
        }
        else
        {
            throw new ForbiddenException("You are not authorized to add salary records.");
        }

        EmployeeSalary employeeSalary = employeeSalaryRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Salary not found"));

        EmployeeSalaryResponse response = EmployeeSalaryMapper.convertEmployeeSalaryToEmployeeSalaryResponse(employeeSalary);
        response.setUsername(userResponse.getFirstName() + " " + userResponse.getLastName());
        return response;
    }

}
