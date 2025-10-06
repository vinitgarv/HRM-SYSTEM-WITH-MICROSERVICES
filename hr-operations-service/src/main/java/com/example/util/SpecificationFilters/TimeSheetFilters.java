package com.example.util.SpecificationFilters;

import com.example.entity.TimeSheet;
import org.springframework.data.jpa.domain.Specification;

public class TimeSheetFilters
{
    public static Specification<TimeSheet> hasStatus(String status)
    {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
