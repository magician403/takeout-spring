package com.example.takeout.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeout.entity.UserEntity;
import com.example.takeout.entity.UserRoleEntity;
import com.example.takeout.mapper.UserMapper;
import com.example.takeout.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserEntity::getUsername, username);
        UserEntity user = userMapper.selectOne(queryWrapper1);
        if (user == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        LambdaQueryWrapper<UserRoleEntity> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.select(UserRoleEntity::getRole);
        queryWrapper2.eq(UserRoleEntity::getUserId, user.getUserId());
        String[] roles = userRoleMapper.selectObjs(queryWrapper2).toArray(new String[0]);
        return User.withUsername(username).password(user.getHashedPassword()).roles(roles).build();
    }
}