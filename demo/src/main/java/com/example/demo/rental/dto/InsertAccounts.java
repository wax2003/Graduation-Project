package com.example.demo.rental.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsertAccounts {

    private String gameName;
    private String accountName;
    private String accountPassword;
    private String accountPicture;
    private String priceHour;
    private String status;
    private LocalDateTime createdAt;
    private String description;

}
