package org.sharon.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharon.demo.bean.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sharon
 * @create 2020-08-30-18:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void MD5Test() {
        String liuxiwen77 = passwordEncoder.encode("Liuxiwen77");
        System.out.println(liuxiwen77);
    }

    @Test
    public void test01() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails details = (MyUserDetails)authenticationToken.getDetails();
        System.out.println(details.getEmail());
    }
}
