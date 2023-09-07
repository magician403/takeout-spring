package com.example.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.takeout.dto.EmployeeDto;
import com.example.takeout.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeEntity> {
    IPage<EmployeeDto> selectEmployeeByName(IPage<EmployeeDto> page, @Param("name") String name);

    EmployeeDto selectEmployeeByUserId(@Param("user_id") Long userId);
}