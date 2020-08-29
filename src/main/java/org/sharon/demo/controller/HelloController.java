package org.sharon.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sharon
 * @create 2020-08-29-17:26
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "index";
    }
}
