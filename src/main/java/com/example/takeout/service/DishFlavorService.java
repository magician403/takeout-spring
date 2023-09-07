package com.example.takeout.service;

import com.example.takeout.mapper.DishFlavorMapper;
import com.example.takeout.mapper.DishMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorService {
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Resource
    private DishMapper dishMapper;
}