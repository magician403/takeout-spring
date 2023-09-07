package com.example.takeout.dto;

import com.example.takeout.util.ValidateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DishDto {
    @NotNull(groups = ValidateGroup.Update.class)
    @Null(groups = ValidateGroup.Insert.class)
    private Long id;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String name;
    @NotNull(groups = ValidateGroup.Insert.class)
    private Long categoryId;
    @NotNull(groups = ValidateGroup.Insert.class)
    private BigDecimal price;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String image;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String description;
    @NotNull(groups = ValidateGroup.Insert.class)
    private Integer status;
    @Valid
    private List<DishFlavorDto> flavors;
    @Null(groups = {ValidateGroup.Insert.class, ValidateGroup.Update.class, Default.class})
    private String categoryName;
}