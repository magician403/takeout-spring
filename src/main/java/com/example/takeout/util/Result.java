package com.example.takeout.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    public static Result<Void> ok() {
        return new Result<>(0, "ok", null);
    }

    public static <R> Result<R> ok(R data) {
        return new Result<>(0, "ok", data);
    }
}
