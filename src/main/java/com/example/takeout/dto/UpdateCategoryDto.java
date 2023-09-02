package com.example.takeout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryDto {
    @NotNull
    private Long id;
    private Byte type;
    private String name;
    private Integer sort;
}