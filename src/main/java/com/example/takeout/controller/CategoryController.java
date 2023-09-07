package com.example.takeout.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.takeout.dto.CategoryDto;
import com.example.takeout.service.CategoryService;
import com.example.takeout.util.Result;
import com.example.takeout.util.ValidateGroup;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('EMPLOYEE')")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping("/update")
    public Result<?> updateCategoryById(@RequestBody @Validated(ValidateGroup.Update.class) CategoryDto categoryDto) {
        categoryService.updateCategoryById(categoryDto);
        return Result.ok();
    }

    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Validated(ValidateGroup.Insert.class) CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
        return Result.ok();
    }

    @GetMapping("/page_query")
    public Result<?> pageCategory(@RequestParam("current_page_number") @NotNull Long currentPageNumber,
                                  @RequestParam("page_size") @NotNull Long pageSize) {
        IPage<CategoryDto> page = categoryService.pageCategory(currentPageNumber, pageSize);
        List<CategoryDto> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok(Map.of("total", total, "records", records));
    }

    @PostMapping("/delete")
    public Result<?> deleteCategoryById(@RequestParam("category_id") @NotNull Long categoryId) {
        categoryService.removeCategoryById(categoryId);
        return Result.ok();
    }

    @PostMapping("/update_status")
    public Result<?> startOrStopCategoryById(@RequestParam("status") @NotNull Integer status,
                                             @RequestParam("id") @NotNull Long id) {
        categoryService.updateStatusById(status, id);
        return Result.ok();
    }

    @GetMapping("/get_by_type")
    public Result<?> getDisplayedCategoryListByType(@RequestParam("type") @NotNull Integer type) {
        List<CategoryDto> categories = categoryService.getCategoryListByType(type);
        return Result.ok(categories);
    }
}