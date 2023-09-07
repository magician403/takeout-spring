package com.example.takeout.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "user")
@Data
public class UserEntity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * hash后的密码
     */
    private String hashedPassword;

    /**
     * 0正常1异常
     */
    private Integer status;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}