package com.example.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.takeout.dto.CategoryDto;
import com.example.takeout.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
    @Select("select id, type, name, sort, status from category")
    IPage<CategoryDto> selectCategoryPage(IPage<CategoryDto> page);

    @Select("select id,type, name,sort,status from category where type = #{type}")
    List<CategoryDto> selectCategoryListByType(@Param("type") Integer type);
}