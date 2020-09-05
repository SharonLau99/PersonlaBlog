package org.sharon.demo.controller;

import org.sharon.demo.bean.Blog;
import org.sharon.demo.bean.Type;
import org.sharon.demo.mapper.BlogMapper;
import org.sharon.demo.service.BlogService;
import org.sharon.demo.service.TagService;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 按照分类来展示博客
 * @author sharon
 * @create 2020-09-03-19:11
 */
@Controller
public class TypeShowController {

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping("/type/{id}")
    public String typePage(@PathVariable(value = "id") Long id,
                           @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                           @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                           Model model) {
        //所有分类
        List<Type> types = typeService.getTypesSortByBlogs(1000);
        model.addAttribute("types", types);
        //刚进入分类列表时的默认值，此时默认展示的是关联最多博客数量的分类列表
        if (id == -1) {
            id = types.get(0).getId();
        }

        model.addAttribute("activeTypeId", id);
        Blog b = new Blog();
        b.setType(new Type(id));
        model.addAttribute("blogs", blogService.getBlogByCondition(pageNo, 5, orderBy, b));
        return "types";
    }

    //在分类列表下进行翻页时，局部刷新页面
    @PostMapping("/type")
    public String changeTypePage(@RequestParam(value = "id") Long id,
                           @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                           @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                           Model model) {

        model.addAttribute("activeTypeId", id);
        Blog b = new Blog();
        b.setType(new Type(id));
        model.addAttribute("blogs", blogService.getBlogByCondition(pageNo, 5, orderBy, b));
        return "types :: blogList";
    }
}
