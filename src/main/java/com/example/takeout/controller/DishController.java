package com.example.takeout.controller;

import com.example.takeout.dto.DishDto;
import com.example.takeout.service.DishService;
import com.example.takeout.util.Result;
import com.example.takeout.util.ValidateGroup;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Validated
public class DishController {
    @Resource
    private DishService dishService;

    @PostMapping("/add")
    public Result<?> addDishAndFlavor(@RequestBody @Validated(ValidateGroup.Insert.class) DishDto dishDto) {
        dishService.addDishAndFlavor(dishDto);
        return Result.ok();
    }

    @GetMapping("/get_list")
    public Result<?> getDishListByCategoryId(@RequestParam("category_id") @NotNull Long categoryId) {
        List<DishDto> dishList = dishService.getDishListByCategoryId(categoryId);
        return Result.ok(dishList);
    }

    @PostMapping("/remove")
    public Result<?> removeDishListById(@RequestBody @NotEmpty List<Long> dishIds) {
        dishService.removeDishListById(dishIds);
        return Result.ok();
    }

    @GetMapping("/get")
    public Result<?> getDishById(@RequestParam("id") @NotNull Long id) {
        DishDto dishDto = dishService.getDishById(id);
        return Result.ok(dishDto);
    }

    @PostMapping("/update")
    public Result<?> updateDishById(@RequestBody @Validated(ValidateGroup.Update.class) DishDto dishDto) {
        dishService.updateDishById(dishDto);
        return Result.ok();
    }
}