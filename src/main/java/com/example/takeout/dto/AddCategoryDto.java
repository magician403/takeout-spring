package com.example.takeout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCategoryDto {
    @NotNull
    private Long id;
    @NotNull
    private Byte type;
    @NotNull
    private String name;
    @NotNull
    private Integer sort;
}