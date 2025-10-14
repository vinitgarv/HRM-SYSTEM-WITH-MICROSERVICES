package com.moonstack.repository;

import com.moonstack.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseRepository extends JpaRepository<Expense,String>, JpaSpecificationExecutor<Expense> {
}
