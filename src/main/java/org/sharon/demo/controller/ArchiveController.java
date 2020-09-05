package org.sharon.demo.controller;

import org.sharon.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 获取时间轴的controller
 * @author sharon
 * @create 2020-09-04-0:06
 */
@Controller
public class ArchiveController {
    @Autowired(required = false)
    BlogService blogService;

    //获取时间轴页面
    @GetMapping("/archive")
    public String archivePage(Model model) {
        model.addAttribute("archiveMap", blogService.getBlogsByYear());
        model.addAttribute("blogCount", blogService.getPublishedBlogCount());
        return "archives";
    }
}
