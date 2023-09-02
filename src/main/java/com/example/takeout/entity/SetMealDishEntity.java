package com.example.takeout.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}