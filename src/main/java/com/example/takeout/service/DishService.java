package com.example.takeout.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeout.constant.DishStatus;
import com.example.takeout.dto.DishDto;
import com.example.takeout.dto.DishFlavorDto;
import com.example.takeout.entity.DishEntity;
import com.example.takeout.entity.DishFlavorEntity;
import com.example.takeout.entity.SetMealDishEntity;
import com.example.takeout.exception.DeleteNotAllowedException;
import com.example.takeout.exception.DishNameDuplicateException;
import com.example.takeout.mapper.DishFlavorMapper;
import com.example.takeout.mapper.DishMapper;
import com.example.takeout.mapper.SetMealDishMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishService {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Resource
    private SetMealDishMapper setMealDishMapper;

    @Transactional
    public void addDishAndFlavor(DishDto dishDto) {
        DishEntity dishEntity = new DishEntity();
        BeanUtil.copyProperties(dishDto, dishEntity);
        try {
            dishMapper.insert(dishEntity);
        } catch (Exception e) {
            throw new DishNameDuplicateException();
        }
        List<DishFlavorDto> flavorsDto = dishDto.getFlavors();
        if (!(flavorsDto != null && flavorsDto.size() > 0)) {
            return;
        }
        List<DishFlavorEntity> flavors = BeanUtil.copyToList(flavorsDto, DishFlavorEntity.class);
        for (DishFlavorEntity flavor : flavors) {
            flavor.setDishId(dishEntity.getId());
            // todo 口味重复
            dishFlavorMapper.insert(flavor);
        }
    }

    public List<DishDto> getDishListByCategoryId(Long categoryId) {
        List<DishDto> dishList = dishMapper.selectDishListByCategoryId(categoryId);
        for (DishDto dish : dishList) {
            Long dishId = dish.getId();
            List<DishFlavorEntity> flavorList = dishFlavorMapper.selectList(
                    new LambdaQueryWrapper<DishFlavorEntity>().select
                            (DishFlavorEntity::getDishId, DishFlavorEntity::getName,
                                    DishFlavorEntity::getValue).eq(DishFlavorEntity::getDishId, dishId));
            List<DishFlavorDto> displayedFlavorList = BeanUtil.copyToList(flavorList, DishFlavorDto.class);
            dish.setFlavors(displayedFlavorList);
        }
        return dishList;
    }

    @Transactional
    public void removeDishListById(List<Long> dishId) {
        // 判断菜品能否删除
        // 1.起售中
        Long statusCount = dishMapper.selectCount(new LambdaQueryWrapper<DishEntity>().in(DishEntity::getId, dishId).eq(DishEntity::getStatus, DishStatus.ENABLE).last("lock in share mode"));
        if (statusCount > 0) {
            throw new DeleteNotAllowedException();
        }
        // 2.菜品被套餐关联
        Long setMealCount = setMealDishMapper.selectCount(new LambdaQueryWrapper<SetMealDishEntity>().in(SetMealDishEntity::getDishId, dishId).last("lock in share mode"));
        if (setMealCount > 0) {
            throw new DeleteNotAllowedException();
        }
        dishMapper.deleteBatchIds(dishId);
    }

    public DishDto getDishById(Long id) {
        DishDto dishDto = dishMapper.selectDishById(id);
        List<DishFlavorEntity> flavorEntities = dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavorEntity>().eq(DishFlavorEntity::getDishId, dishDto.getId()));
        List<DishFlavorDto> flavors = BeanUtil.copyToList(flavorEntities, DishFlavorDto.class);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Transactional
    public void updateDishById(DishDto dishDto) {
        DishEntity dishEntity = new DishEntity();
        BeanUtil.copyProperties(dishDto, dishEntity);
        // todo 异常处理
        dishMapper.updateById(dishEntity);
        dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavorEntity>().eq(DishFlavorEntity::getDishId, dishDto.getId()));
        if (CollectionUtil.isNotEmpty(dishDto.getFlavors())) {
            for (DishFlavorDto dishFlavor : dishDto.getFlavors()) {
                DishFlavorEntity dishFlavorEntity = BeanUtil.copyProperties(dishFlavor, DishFlavorEntity.class);
                // todo 异常处理
                dishFlavorMapper.insert(dishFlavorEntity);
            }
        }
    }
}