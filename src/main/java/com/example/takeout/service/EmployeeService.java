package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.constant.Deleted;
import com.example.takeout.constant.UserStatus;
import com.example.takeout.dto.AddEmployeeDto;
import com.example.takeout.dto.EmployeeDisplayedDto;
import com.example.takeout.dto.UpdateEmployeeDto;
import com.example.takeout.entity.EmployeeEntity;
import com.example.takeout.entity.UserEntity;
import com.example.takeout.entity.UserRoleEntity;
import com.example.takeout.exception.UserIdNotExistException;
import com.example.takeout.exception.UsernameExistException;
import com.example.takeout.mapper.EmployeeMapper;
import com.example.takeout.mapper.UserMapper;
import com.example.takeout.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@PreAuthorize("hasRole('EMPLOYEE')")
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
    public void addEmployee(AddEmployeeDto addEmployeeDto, Long createdUser) {
        EmployeeEntity employee = new EmployeeEntity();
        UserEntity user = new UserEntity();
        UserRoleEntity userRole = new UserRoleEntity();

        BeanUtil.copyProperties(addEmployeeDto, employee);
        BeanUtil.copyProperties(addEmployeeDto, user);

        employee.setDeleted(Deleted.FALSE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(createdUser);
        employee.setUpdateUser(createdUser);

        user.setDeleted(Deleted.FALSE);
        user.setStatus(UserStatus.ENABLE);
        user.setHashedPassword(passwordEncoder.encode(addEmployeeDto.getPassword()));

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

    public Page<EmployeeDisplayedDto> pageQueryByName(String name, long currentPageNumber, long pageSize) {
        Page<EmployeeDisplayedDto> page = new Page<>();
        page.setCurrent(currentPageNumber);
        page.setSize(pageSize);
        employeeMapper.selectDisplayedInfoByName(page, name);
        return page;
    }

    public EmployeeDisplayedDto getDisplayedInfoByUserId(Long userId) {
        return employeeMapper.getDisplayedInfoByUserId(userId);
    }

    @Transactional
    public void updateEmployeeByUserId(Long updateUser, UpdateEmployeeDto updateEmployeeDto) {
        EmployeeEntity employee = new EmployeeEntity();
        BeanUtil.copyProperties(updateEmployeeDto, employee);
        employee.setUpdateUser(updateUser);
        employee.setUpdateTime(LocalDateTime.now());

        try {
            employeeMapper.update(employee, new LambdaQueryWrapper<EmployeeEntity>().eq(EmployeeEntity::getUserId, employee.getUserId()));
        } catch (Exception e) {
            throw new UserIdNotExistException();
        }

        UserEntity user = new UserEntity();
        BeanUtil.copyProperties(updateEmployeeDto, user);
        if (updateEmployeeDto.getPassword() != null) {
            user.setHashedPassword(passwordEncoder.encode(updateEmployeeDto.getPassword()));
        }
        if (updateEmployeeDto.getUsername() != null || updateEmployeeDto.getPassword() != null) {
            try {
                userMapper.updateById(user);
            } catch (Exception e) {
                throw new UserIdNotExistException();
            }
        }
    }
}