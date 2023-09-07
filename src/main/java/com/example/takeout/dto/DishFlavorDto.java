package com.example.takeout.dto;

import com.example.takeout.util.ValidateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class DishFlavorDto {
    @Null(groups = ValidateGroup.Insert.class)
    @NotNull(groups = ValidateGroup.Update.class)
    private Long id;
    @NotNull(groups = ValidateGroup.Insert.class)
    private Long dishId;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String name;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String value;
}