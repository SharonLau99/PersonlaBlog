package org.sharon.demo.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆失败处理handler
 * @author sharon
 * @create 2020-09-05-14:12
 */
@Component
public class MyCustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    protected final Logger myBlogLogger = LoggerFactory.getLogger("MyBlogLogger");

    private String defaultFailureUrl;

    public MyCustomAuthenticationFailureHandler() {
    }

    public MyCustomAuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            myBlogLogger.debug("No failure URL set, sending 401 Unauthorized error");
            //defaultFailureUrl为空，这里简单处理一下，设置成了转发到登陆页面
            defaultFailureUrl = "/admin/login/failure";
        }

        //开始设置返回信息，并转发
        if (exception instanceof AccountExpiredException) {
            //账号过期
            request.setAttribute("message", "用户信息已过期。");
        } else if (exception instanceof BadCredentialsException) {
            //密码错误
            request.setAttribute("message", "用户名或密码错误。");
        } else if (exception instanceof InternalAuthenticationServiceException) {
            //找不到用户名
            request.setAttribute("message", "用户名或密码错误。");
        } else{
            //其他错误
            request.setAttribute("message", "未知错误，请联系站主。");
        }
        myBlogLogger.debug("Forwarding to " + defaultFailureUrl);
        request.getRequestDispatcher(defaultFailureUrl)
                .forward(request, response);
    }
}
