package com.example.takeout.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "set_meal_dish")
@Data
public class SetMealDishEntity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 套餐id
     */
    private Long setMealId;

    /**
     * 菜品id
     */
    private Long dishId;

    /**
     * 菜品份数
     */
    private Integer copies;
}