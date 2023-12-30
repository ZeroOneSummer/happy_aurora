package com.aurora.happy.service.impl;

import com.aurora.happy.annotation.SysLog;
import com.aurora.happy.mapper.UserMapper;
import com.aurora.happy.pojo.User;
import com.aurora.happy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @SysLog(spelValue = "'用户名称:' + #user.name")
    @Override
    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }
}