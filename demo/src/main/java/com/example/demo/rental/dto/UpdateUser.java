package com.example.demo.rental.dto;

import lombok.Data;

@Data
public class UpdateUser {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
