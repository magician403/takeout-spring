package com.example.takeout.exception;

public class UsernamePasswordException extends ServiceException{
    public UsernamePasswordException(){
        setCode(3000);
        setMsg("用户名或者密码错误");
    }
}
