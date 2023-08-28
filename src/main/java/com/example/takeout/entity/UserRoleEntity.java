package com.example.takeout.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName(value ="user_role")
@Data
public class UserRoleEntity implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色
     */
    private String role;
}