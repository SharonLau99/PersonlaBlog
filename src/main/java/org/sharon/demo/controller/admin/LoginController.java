package org.sharon.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆失败时转发到该页面
 * @author sharon
 * @create 2020-08-29-17:26
 */
@Controller
public class LoginController {

    @PostMapping("/admin/login/failure")
    public String failure(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object message = request.getAttribute("message");
//        System.out.println("错误信息" + message);
        model.addAttribute("message", message);
        return "admin/login";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }
}
