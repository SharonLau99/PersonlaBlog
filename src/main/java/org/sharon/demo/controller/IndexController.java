package org.sharon.demo.controller;

import org.sharon.demo.mapper.BlogMapper;
import org.sharon.demo.mapper.TagMapper;
import org.sharon.demo.mapper.TypeMapper;
import org.sharon.demo.service.BlogService;
import org.sharon.demo.service.TagService;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 首页和博客详情页的controller
 * @author sharon
 * @create 2020-09-02-20:49
 */
@Controller
public class IndexController {
    @Autowired(required = false)
    BlogService blogService;

    @Autowired(required = false)
    TagService tagService;

    @Autowired(required = false)
    TypeService typeService;

    //进入首页
    @GetMapping(value = {"/", "/index"})
    public String indexPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                            @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                            Model model){
        //标签
        model.addAttribute("tags", tagService.getTagSortByBlogs(10));
        //分类
        model.addAttribute("types", typeService.getTypesSortByBlogs(6));
        //博客
        model.addAttribute("blogs", blogService.getAllBlogs(pageNo, 5, orderBy));
        //推荐博客
        model.addAttribute("recommendBlogs", blogService.getRecommendBlogs(6));
//        System.out.println(blogService.getAllBlogs(pageNo, 5, orderBy).getTotal());
        return "index";
    }

    //首页翻页时局部刷新页面
    @PostMapping("/blogs")
    public String blogsPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                            @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                            Model model){
        //博客
        model.addAttribute("blogs", blogService.getAllBlogs(pageNo, 5, orderBy));
        return "index :: blogList";
    }

    //跳转到搜索结果页面，可以是get或者post，如果是get，默认展示全部博客
    @RequestMapping("/search")
    public String searchPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                            @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                            @RequestParam(value = "keywords", defaultValue = "") String keywords,
                            Model model){
        //博客
        model.addAttribute("keywords", keywords);
        model.addAttribute("blogs", blogService.getBlogsBySearch(pageNo, 5, orderBy, keywords));
        return "search";
    }

    //在搜索结果页面翻页，局部更新列表
    @PostMapping("/search/blogs")
    public String searchBlogs(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                              @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                              @RequestParam(value = "keywords", defaultValue = "") String keywords,
                              Model model){
        //博客
//        System.out.println(pageNo);
        model.addAttribute("blogs", blogService.getBlogsBySearch(pageNo, 5, orderBy, keywords));
        return "search :: blogList";
    }

    //博客详情页
    @GetMapping("/blog/{id}")
    public String getBlog(@PathVariable(value = "id") Long id,
                              Model model){
        //博客
        model.addAttribute("blog", blogService.getBlogAndConvert(id));
        return "blog";
    }

    //底部最新博客推荐
    //博客详情页
    @GetMapping("/footer/latestRecommendBlog")
    public String getLatestRecommendBlogOnFooter(Model model){
        //博客
        model.addAttribute("newBlogs", blogService.getRecommendBlogs(3));
        return "common :: newBlogList";
    }
}
