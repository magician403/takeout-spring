package com.example.takeout.exception;

public class UsernameExistException extends ServiceException {
    public UsernameExistException() {
        setCode(1000);
        setMsg("用户名已经存在");
    }
}