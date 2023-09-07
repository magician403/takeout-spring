package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.constant.CategoryStatus;

import com.example.takeout.dto.CategoryDto;
import com.example.takeout.entity.CategoryEntity;
import com.example.takeout.entity.DishEntity;
import com.example.takeout.entity.SetMealEntity;
import com.example.takeout.exception.CategoryNameDuplicateException;
import com.example.takeout.exception.CategoryNotFoundException;
import com.example.takeout.exception.DeleteNotAllowedException;
import com.example.takeout.mapper.CategoryMapper;
import com.example.takeout.mapper.DishMapper;
import com.example.takeout.mapper.SetMealMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetMealMapper setMealMapper;

    public void updateCategoryById(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        BeanUtil.copyProperties(categoryDto, category);
        try {
            categoryMapper.updateById(category);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }
    }

    public void addCategory(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        BeanUtil.copyProperties(categoryDto, category);
        category.setStatus(CategoryStatus.DISABLE);
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            throw new CategoryNameDuplicateException();
        }
    }

    public IPage<CategoryDto> pageCategory(long currentPageNumber, long pageSize) {
        Page<CategoryDto> page = new Page<>(currentPageNumber, pageSize);
        categoryMapper.selectCategoryPage(page);
        return page;
    }

    @Transactional
    public void removeCategoryById(Long categoryId) {
        Long dishCount = dishMapper.selectCount(new LambdaQueryWrapper<DishEntity>().eq(DishEntity::getCategoryId, categoryId).last("lock in share mode"));
        if (dishCount != 0) {
            throw new DeleteNotAllowedException();
        }
        Long setMealCount = setMealMapper.selectCount(new LambdaQueryWrapper<SetMealEntity>().eq(SetMealEntity::getCategoryId, categoryId).last("lock in share mode"));
        if (setMealCount != 0) {
            throw new DeleteNotAllowedException();
        }
        try {
            categoryMapper.deleteById(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }
    }

    public void updateStatusById(Integer status, Long id) {
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        category.setStatus(status);
        try {
            categoryMapper.updateById(category);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }
    }

    public List<CategoryDto> getCategoryListByType(Integer type) {
        return categoryMapper.selectCategoryListByType(type);
    }
}