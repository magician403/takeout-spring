package com.example.takeout.aspect;

import com.example.takeout.constant.UserDetailsKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.example.takeout.mapper.*.*(..))")
    public void pointcutBaseMethod() {
    }

    @Before("pointcutBaseMethod()")
    public void autoFill(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        if (!methodName.startsWith("insert") && !methodName.startsWith("update")) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Map<String, Object> details = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long userId = (Long) details.get(UserDetailsKey.USER_ID);
        LocalDateTime now = LocalDateTime.now();
        for (Object arg : args) {
            if (!arg.getClass().getSimpleName().endsWith("Entity")) {
                continue;
            }
            try {
                Method setCreateTime = arg.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setCreateUser = arg.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateUser = arg.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, userId);
                if (methodName.startsWith("insert")) {
                    setCreateTime.invoke(arg, now);
                    setCreateUser.invoke(arg, userId);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.warn(e.getMessage());
            }
        }
    }
}