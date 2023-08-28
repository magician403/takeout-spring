package com.example.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeout.constant.UserDetailsKeyConstant;
import com.example.takeout.dto.LoginDto;
import com.example.takeout.entity.UserEntity;
import com.example.takeout.exception.UsernamePasswordException;
import com.example.takeout.service.UserService;
import com.example.takeout.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Valid
public class LoginController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserService userService;
    @Resource
    private SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    public Result<Void> login(@RequestBody @Valid @NotNull LoginDto loginDto,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        UsernamePasswordAuthenticationToken unAuthenticatedToken = new UsernamePasswordAuthenticationToken(username, password);
        UsernamePasswordAuthenticationToken authenticatedToken;
        try {
            authenticatedToken = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(unAuthenticatedToken);
        } catch (AuthenticationException e) {
            throw new UsernamePasswordException();
        }
        Map<String, Object> details = new HashMap<>();
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserEntity::getUserId).eq(UserEntity::getUsername, username);
        Long userId = userService.getOne(queryWrapper).getUserId();
        details.put(UserDetailsKeyConstant.USER_ID, userId);
        authenticatedToken.setDetails(details);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticatedToken);
        securityContextRepository.saveContext(securityContext, request, response);
        return Result.ok(null);
    }
}