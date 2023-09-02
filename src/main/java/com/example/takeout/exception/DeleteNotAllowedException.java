package com.example.takeout.exception;

public class DeleteNotAllowedException extends ServiceException {
    public DeleteNotAllowedException() {
        setCode(7000);
        setMsg("当前分类中还有菜品，不能删除");
    }
}