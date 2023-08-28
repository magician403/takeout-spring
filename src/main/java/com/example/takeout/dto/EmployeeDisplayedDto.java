package com.example.takeout.dto;

import lombok.Data;

@Data
public class EmployeeDisplayedDto {
    private Long userId;
    private String username;
    private String name;
    private String phone;
    private Byte sex;
    private String idNumber;
}
