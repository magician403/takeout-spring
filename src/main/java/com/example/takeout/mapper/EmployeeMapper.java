package com.example.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.takeout.dto.EmployeeDisplayedDto;
import com.example.takeout.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeEntity> {
    IPage<EmployeeDisplayedDto> selectDisplayedInfoByName(IPage<EmployeeDisplayedDto> page, @Param("name") String name);

    EmployeeDisplayedDto getDisplayedInfoByUserId(@Param("user_id") Long userId);
}