package org.sharon.demo.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sharon.demo.bean.MyUserDetails;
import org.sharon.demo.bean.User;
import org.sharon.demo.exception.MyUsernameNotFoundException;
import org.sharon.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义spring security的UserDetailsService实现方法，自定义登陆后session中保存的用户信息
 * @author sharon
 * @create 2020-08-30-15:27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required = false)
    UserMapper userMapper;
    protected final Logger myBlogLogger = LoggerFactory.getLogger("MyBlogLogger");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            myBlogLogger.debug("\"{}\" 用户名不存在", username);
            throw new MyUsernameNotFoundException("用户名 \"" + username + "\" 不存在");
        }


        MyUserDetails userDetails = new MyUserDetails(
                username, user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getType())
        );

        userDetails.setId(user.getId());
        userDetails.setAvatar(user.getAvatar());
        userDetails.setNickname(user.getNickname());
        userDetails.setEmail(user.getEmail());
        return userDetails;
    }

    // 得到用户所拥有的权限
    public Collection<GrantedAuthority> getAuthorities(int access){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //首先所有的都拥有用户权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //如果是0，那就是用户加管理员
        if(access==1) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        //如果是2，那就是介于管理员和普通用户之间的权限，相当于可以理解为vip吧
        if(access == 2) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TWO"));
        }

        return authorities;
    }
}
