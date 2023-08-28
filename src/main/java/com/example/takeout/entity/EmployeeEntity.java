package com.example.takeout.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "employee")
@Data
public class EmployeeEntity implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别,0为男,1为女
     */
    private Byte sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    private Long createUser;

    /**
     * 修改人id
     */
    private Long updateUser;

    /**
     * 是否删除
     */
    private Byte deleted;
}