package org.sharon.demo.config;

import org.sharon.demo.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MD5密码加密存入数据库
 * @author sharon
 * @create 2020-08-30-13:49
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encode((String)rawPassword));
    }
}
