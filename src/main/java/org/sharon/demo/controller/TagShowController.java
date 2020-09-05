package org.sharon.demo.controller;

import org.sharon.demo.bean.Blog;
import org.sharon.demo.bean.Tag;
import org.sharon.demo.bean.Type;
import org.sharon.demo.service.BlogService;
import org.sharon.demo.service.TagService;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 按照标签来展示博客
 * @author sharon
 * @create 2020-09-03-19:11
 */
@Controller
public class TagShowController {

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @GetMapping("/tag/{id}")
    public String typePage(@PathVariable(value = "id", required = false) Long id,
                           @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                           @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                           Model model) {
        //展示所有标签
        List<Tag> tags = tagService.getTagSortByBlogs(1000);
        model.addAttribute("tags", tags);
        //刚进入标签列表时的默认值，此时默认展示的是关联最多博客数量的分类列表
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("activeTagId", id);
        Blog b = new Blog();
        b.setType(new Type(id));
        model.addAttribute("blogs", blogService.getBlogByTag(pageNo, 5, orderBy, id));
        return "tags";
    }

    //在标签列表下进行翻页时，局部刷新页面
    @PostMapping("/tag")
    public String changeTagPage(@RequestParam(value = "id") Long id,
                           @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                           @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                           Model model) {
        //所有标签
        System.out.println(id);
        model.addAttribute("activeTagId", 5);
        model.addAttribute("blogs", blogService.getBlogByTag(pageNo, 5, orderBy, id));
        return "tags :: blogList";
    }
}
