package com.example.takeout.exception;

public class CategoryNotFoundException extends ServiceException {
    public CategoryNotFoundException() {
        setCode(6000);
        setMsg("分类id不存在");
    }
}
