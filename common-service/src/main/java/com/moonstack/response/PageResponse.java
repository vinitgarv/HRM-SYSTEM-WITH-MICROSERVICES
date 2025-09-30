package com.moonstack.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PageResponse<T>
{
    private List<T> data;
    private Integer elements;
    private Integer pageNo;
    private Integer limit;
    private Integer totalPages;
}
