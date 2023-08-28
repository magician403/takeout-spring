package com.example.takeout.exception;

public class SystemException extends ServiceException {
    public SystemException() {
        setCode(-1);
        setMsg("系统异常");
    }
}