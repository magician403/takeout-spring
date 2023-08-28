package com.example.takeout.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArgumentCheckException extends RuntimeException {
    private Integer code;
    private String msg;
    public ArgumentCheckException() {
        setCode(4000);
        setMsg("参数输入错误");
    }
}
