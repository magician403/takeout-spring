package com.example.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeout.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

}