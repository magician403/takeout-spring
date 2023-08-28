package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Tuple;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeout.dto.AddEmployeeDto;
import com.example.takeout.dto.EmployeeDisplayedDto;
import com.example.takeout.entity.EmployeeEntity;
import com.example.takeout.entity.UserEntity;
import com.example.takeout.entity.UserRoleEntity;
import com.example.takeout.exception.SystemException;
import com.example.takeout.exception.UsernameExistException;
import com.example.takeout.mapper.EmployeeMapper;
import com.example.takeout.mapper.UserMapper;
import com.example.takeout.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class EmployeeService extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements IService<EmployeeEntity> {
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void addEmployee(AddEmployeeDto addEmployeeDto, Long createdUser, Long updateUser) {
        EmployeeEntity employee = new EmployeeEntity();
        UserEntity user = new UserEntity();
        UserRoleEntity userRole = new UserRoleEntity();

        BeanUtil.copyProperties(addEmployeeDto, employee);
        BeanUtil.copyProperties(addEmployeeDto, user);

        employee.setDeleted((byte) 0);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(createdUser);
        employee.setUpdateUser(updateUser);

        user.setDeleted((byte) 0);
        user.setHashedPassword(passwordEncoder.encode(addEmployeeDto.getPassword()));

        try {
            userMapper.insert(user);
        } catch (RuntimeException e) {
            throw new UsernameExistException();
        }

        employee.setUserId(user.getUserId());
        userRole.setUserId(user.getUserId());
        userRole.setRole("EMPLOYEE");

        try {
            employeeMapper.insert(employee);
        } catch (RuntimeException e) {
            throw new SystemException();
        }

        try {
            userRoleMapper.insert(userRole);
        } catch (RuntimeException e) {
            throw new SystemException();
        }
    }

    public Tuple pageQueryByName(String name, long currentPageNumber, long pageSize) {
        Page<EmployeeEntity> page = new Page<>();
        page.setCurrent(currentPageNumber);
        page.setSize(pageSize);
        page.setTotal(pageSize);
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(EmployeeEntity::getUsername, EmployeeEntity::getName,
                EmployeeEntity::getPhone, EmployeeEntity::getSex, EmployeeEntity::getIdNumber, EmployeeEntity::getUserId);
        queryWrapper.eq(EmployeeEntity::getName, name);
        List<EmployeeEntity> employeeEntities = employeeMapper.selectList(page, queryWrapper);
        Long totalNumber = employeeMapper.selectCount(new LambdaQueryWrapper<EmployeeEntity>().eq(EmployeeEntity::getName, name));
        List<EmployeeDisplayedDto> employeeDisplayedDtos = BeanUtil.copyToList(employeeEntities, EmployeeDisplayedDto.class);
        Tuple returnValue = new Tuple(totalNumber, employeeDisplayedDtos);
        return returnValue;
    }

    public EmployeeDisplayedDto getEmployeeById(Long userId) {
        EmployeeEntity employee = employeeMapper.selectById(userId);
        return BeanUtil.copyProperties(employee, EmployeeDisplayedDto.class);
    }
}