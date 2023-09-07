package com.example.takeout.exception;

public class DeleteNotAllowedException extends ServiceException {
    public DeleteNotAllowedException() {
        setCode(7000);
        setMsg("不能删除");
    }
}