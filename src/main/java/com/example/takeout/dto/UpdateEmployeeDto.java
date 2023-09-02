package com.example.takeout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateEmployeeDto {
    @NotNull
    private Long userId;
    private String name;
    private String username;
    private String password;
    private String phone;
    @Range(min = 0, max = 1)
    private Byte sex;
    @Length(min = 18, max = 18)
    private String idNumber;
}
