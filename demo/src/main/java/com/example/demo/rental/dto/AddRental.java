package com.example.demo.rental.dto;


import lombok.Data;

@Data
public class AddRental {

    private Integer userId;

    private Integer gameAccountId;
    private Integer time;
    private Double price;
    private String status;
}
