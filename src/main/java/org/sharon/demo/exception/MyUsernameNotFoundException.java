package org.sharon.demo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义找不到用户名的exception
 * @author sharon
 * @create 2020-09-05-15:37
 */
public class MyUsernameNotFoundException extends AuthenticationException {
    public MyUsernameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyUsernameNotFoundException(String msg) {
        super(msg);
    }
}
