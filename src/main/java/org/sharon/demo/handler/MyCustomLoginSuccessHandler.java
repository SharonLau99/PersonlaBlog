package org.sharon.demo.handler;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 自定义登陆成功后的handler，还没做，但不影响目前的使用，后续需要优化可以再写。
 * @author sharon
 * @create 2020-08-31-9:51
 */
public class MyCustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public MyCustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }
}

