package com.moonstack.utils.SpecificationFilters;

import com.moonstack.entity.Message;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class MessageFilters
{
    public static Specification<Message> filter(String value) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(value)) { // if value is null or empty
                return cb.conjunction(); // return all records
            }
            return cb.like(root.get("value"), "%" + value + "%"); // contains match
        };
    }
}
