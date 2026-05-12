package com.example.demo.rental.dto;

import lombok.Data;

@Data
public class UpdateAccounts {
    private Integer id;
    private String gameName;
    private String accountName;
    private String accountPassword;
    private String description;
    private String priceHour;
    private String status;
}
