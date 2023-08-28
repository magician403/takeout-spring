package com.example.takeout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class AddEmployeeDto {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String phone;
    @Range(min = 0, max = 1)
    @NotNull
    private Byte sex;
    @NotNull
    @Length(min = 18, max = 18)
    private String idNumber;
}
