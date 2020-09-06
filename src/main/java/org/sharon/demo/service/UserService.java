package org.sharon.demo.service;

import org.sharon.demo.bean.User;
import org.sharon.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sharon
 * @create 2020-08-30-9:26
 */
public interface UserService {

    public User getUser(String username, String password);
}
