package com.example.takeout.dto;

import com.example.takeout.util.ValidateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class EmployeeDto {
    @Null(groups = ValidateGroup.Insert.class)
    @NotNull(groups = ValidateGroup.Update.class)
    private Long userId;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String name;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String username;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String password;
    @NotNull(groups = ValidateGroup.Insert.class)
    private String phone;
    @Range(min = 0, max = 1, groups = {ValidateGroup.Insert.class, ValidateGroup.Update.class})
    @NotNull(groups = ValidateGroup.Insert.class)
    private Byte sex;
    @Length(min = 18, max = 18, groups = {ValidateGroup.Insert.class, ValidateGroup.Update.class})
    @NotNull(groups = ValidateGroup.Insert.class)
    private String idNumber;
}
