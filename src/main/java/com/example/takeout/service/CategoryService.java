package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeout.constant.CategoryStatus;
import com.example.takeout.dto.AddCategoryDto;
import com.example.takeout.dto.UpdateCategoryDto;
import com.example.takeout.entity.CategoryEntity;
import com.example.takeout.entity.DishEntity;
import com.example.takeout.exception.CategoryNotFoundException;
import com.example.takeout.exception.DeleteNotAllowedException;
import com.example.takeout.mapper.CategoryMapper;
import com.example.takeout.mapper.DishMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public void updateCategoryById(UpdateCategoryDto updateCategoryDto, Long updateUser) {
        CategoryEntity category = new CategoryEntity();
        BeanUtil.copyProperties(updateCategoryDto, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(updateUser);
        try {
            categoryMapper.updateById(category);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }
    }

    public void addCategory(AddCategoryDto addCategoryDto, Long createUser) {
        CategoryEntity category = new CategoryEntity();
        BeanUtil.copyProperties(addCategoryDto, category);
        category.setStatus((CategoryStatus.DISABLE));
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(createUser);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(createUser);
        categoryMapper.insert(category);
    }

    public void deleteCategoryById(Long id) {
        try {
            categoryMapper.deleteById(id);
        } catch (Exception e) {
            throw new DeleteNotAllowedException();
        }
    }
}