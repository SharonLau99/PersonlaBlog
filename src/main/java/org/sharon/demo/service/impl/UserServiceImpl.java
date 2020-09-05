package org.sharon.demo.service.impl;

import org.sharon.demo.bean.User;
import org.sharon.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sharon
 * @create 2020-08-31-15:26
 */
@Service
public class UserServiceImpl {
    @Autowired(required = false)
    UserMapper userMapper;
    //获得用户
    public User getUser(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, password);
    }
}
