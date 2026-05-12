package com.example.demo.rental.dto;

import lombok.Data;

@Data
public class QueryAccounts {

    private Long pageNumber;
    private Long pageSize;
    private String gameName;

}
