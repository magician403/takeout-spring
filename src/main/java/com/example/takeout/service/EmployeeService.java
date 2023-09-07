package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.constant.Deleted;
import com.example.takeout.constant.UserStatus;
import com.example.takeout.dto.EmployeeDto;
import com.example.takeout.entity.EmployeeEntity;
import com.example.takeout.entity.UserEntity;
import com.example.takeout.entity.UserRoleEntity;
import com.example.takeout.exception.UserIdNotExistException;
import com.example.takeout.exception.UsernameExistException;
import com.example.takeout.mapper.EmployeeMapper;
import com.example.takeout.mapper.UserMapper;
import com.example.takeout.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void addEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = new EmployeeEntity();
        UserEntity user = new UserEntity();
        UserRoleEntity userRole = new UserRoleEntity();

        BeanUtil.copyProperties(employeeDto, employee);
        BeanUtil.copyProperties(employeeDto, user);

        employee.setDeleted(Deleted.FALSE);

        user.setDeleted(Deleted.FALSE);
        user.setStatus(UserStatus.ENABLE);
        user.setHashedPassword(passwordEncoder.encode(employeeDto.getPassword()));

        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new UsernameExistException();
        }

        employee.setUserId(user.getId());
        userRole.setUserId(user.getId());
        userRole.setRole("EMPLOYEE");

        employeeMapper.insert(employee);
        userRoleMapper.insert(userRole);
    }

    public Page<EmployeeDto> pageEmployeeByName(String name, long currentPageNumber, long pageSize) {
        Page<EmployeeDto> page = new Page<>(currentPageNumber, pageSize);
        employeeMapper.selectEmployeeByName(page, name);
        return page;
    }

    public EmployeeDto getEmployeeByUserId(Long userId) {
        return employeeMapper.selectEmployeeByUserId(userId);
    }

    @Transactional
    public void updateEmployeeByUserId(EmployeeDto employeeDto) {
        EmployeeEntity employee = new EmployeeEntity();
        BeanUtil.copyProperties(employeeDto, employee);

        try {
            employeeMapper.update(employee, new LambdaQueryWrapper<EmployeeEntity>().eq(EmployeeEntity::getUserId, employee.getUserId()));
        } catch (Exception e) {
            throw new UserIdNotExistException();
        }
        UserEntity user = new UserEntity();
        BeanUtil.copyProperties(employeeDto, user);
        if (employeeDto.getPassword() != null) {
            user.setHashedPassword(passwordEncoder.encode(employeeDto.getPassword()));
        }
        if (employeeDto.getUsername() != null || employeeDto.getPassword() != null) {
            try {
                userMapper.updateById(user);
            } catch (Exception e) {
                throw new UserIdNotExistException();
            }
        }
    }
}