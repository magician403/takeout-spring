package com.example.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeout.dto.DishDto;
import com.example.takeout.entity.DishEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<DishEntity> {
    @Select("""
            select dish.id , dish.name, dish.category_id, dish.price, dish.image, dish.description,
            dish.status, category.name category_name from dish inner join category
            on dish.category_id = category.id where category_id = #{category_id}
            """)
    List<DishDto> selectDishListByCategoryId(@Param("category_id") Long categoryId);

    @Select("""
            select dish.id , dish.name, dish.category_id, dish.price, dish.image, dish.description,
            dish.status, category.name category_name from dish inner join category
            on dish.category_id = category.id where dish.id = #{id}
            """)
    DishDto selectDishById(@Param("id") Long id);
}