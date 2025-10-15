package com.moonstack.util;

import com.moonstack.entity.Expense;
import com.moonstack.enums.PurchaseStatus;
import org.springframework.data.jpa.domain.Specification;

public class ExpenseSpecification {
    public static Specification<Expense> hasStatus(String purchaseStatus) {
        return (root, query, criteriaBuilder) ->
                purchaseStatus == null ? null : criteriaBuilder.equal(root.get("purchaseStatus"), purchaseStatus);
    }
}