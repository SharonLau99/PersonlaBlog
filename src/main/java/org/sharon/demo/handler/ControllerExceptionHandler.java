package org.sharon.demo.handler;

import org.sharon.demo.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理handler，后续还可以继续优化、更新更多地异常。解耦，使逻辑更清晰，也更好维护
 * @author sharon
 * @create 2020-08-29-19:43
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    protected final Logger myBlogLogger = LoggerFactory.getLogger("MyBlogLogger");

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundException(HttpServletRequest request, Exception e) throws Exception {
        myBlogLogger.error("Request URL : {}, Exception : {}", request.getRequestURI(), e);

        //抛出异常，让spring boot转到404页面。
        throw e;
    }

}
