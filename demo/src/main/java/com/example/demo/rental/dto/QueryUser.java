package com.example.demo.rental.dto;

import lombok.Data;

@Data
public class QueryUser {
    private Long pageNumber;
    private Long pageSize;
    private String username;
}
