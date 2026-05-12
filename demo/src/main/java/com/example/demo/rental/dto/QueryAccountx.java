package com.example.demo.rental.dto;


import lombok.Data;

@Data
public class QueryAccountx {

    private Long pageNumber;
    private Long pageSize;
    private String gameName;
    private Double priceMin; // 范围下限
    private Double priceMax; // 范围上限
    private String status;  //状态 不可租用的直接不返回
    private String description;//部分描述

}
