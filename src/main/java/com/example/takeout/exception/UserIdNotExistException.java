package com.example.takeout.exception;

public class UserIdNotExistException extends ServiceException{
    public UserIdNotExistException(){
        setCode(5000);
        setMsg("用户id不存在");
    }
}
