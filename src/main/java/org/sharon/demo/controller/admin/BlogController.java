package org.sharon.demo.controller.admin;

import org.apache.ibatis.annotations.Delete;
import org.sharon.demo.bean.Blog;
import org.sharon.demo.bean.MyUserDetails;
import org.sharon.demo.bean.User;
import org.sharon.demo.exception.NotFoundException;
import org.sharon.demo.service.BlogService;
import org.sharon.demo.service.TagService;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 管理页面对博客管理的controller
 * @author sharon
 * @create 2020-09-01-11:00
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

    //展示全部博客列表
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                        @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                        Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("page", blogService.getAllBlogs(pageNo, 5, orderBy));
//        System.out.println(blogService.getAllBlogs(1, 5, orderBy));
        return "admin/blogs";
    }

    //搜索或者跳转页面时局部更新列表
    @PostMapping("/blogs/search")
    public String searchBlogs(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "orderBy", defaultValue = "update_time desc") String orderBy,
                              Blog blog,
                              Long typeId,
                              Model model) {
//        System.out.println(typeId);
        if (typeId != null) {
            blog.setType(typeService.getTypeById(typeId));
        }
        //System.out.println(blog);
        model.addAttribute("page", blogService.getBlogByCondition(pageNo, 5, orderBy, blog));
        return "admin/blogs :: blogList";
    }

    //跳转到博客编辑页面
    @GetMapping("/blog")
    public String editPage(@RequestParam(value = "id", required = false) Long id,
                           Model model) {
        if (id != null) {
            Blog blog = blogService.getBlogById(id);
            if (blog == null) {
                //编辑的博客查找不到
                throw new NotFoundException("您要编辑的博客不存在。");
            } else {
//            System.out.println(blog);
                blog.tagIdsInit();
//            System.out.println(blog.getTagIds());
                model.addAttribute("blog", blog);
            }
        }
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("types", typeService.getAllTypes());
        return "admin/blog-input";
    }

    //新加博客
    @PostMapping("/blog")
    public String insertBlog(Blog blog, String tagIds, RedirectAttributes attributes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
//        System.out.println(tagIds);
        blog.setUser(new User(user.getId()));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.getTagFromList(tagService.convertToList(tagIds)));
        Blog b = blogService.insertBlog(blog, tagService.convertToList(tagIds));
        if (b == null) {
            model.addAttribute("blog", blog);
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("message", "新增失败，标题不能重复。");
            return "admin/blog-input";
        } else {
            attributes.addFlashAttribute("message", "新增成功");
            return "redirect:/admin/blogs";
        }
    }

    //编辑博客
    @PutMapping("/blog")
    public String updateBlog(Blog blog, String tagIds, RedirectAttributes attributes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        blog.setUser(new User(user.getId()));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.getTagFromList(tagService.convertToList(tagIds)));
        Blog b = blogService.updateBlog(blog, tagService.convertToList(tagIds));
        if (b == null) {
            model.addAttribute("blog", blog);
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("message", "保存失败，标题不能重复。");
            return "admin/blog-input";
        } else {
            attributes.addFlashAttribute("message", "保存成功");
            return "redirect:/admin/blogs";
        }
    }

    //删除博客
    @DeleteMapping("/blog/{id}")
    public String deleteBlog(@PathVariable(value = "id")String blogId, RedirectAttributes attributes, Model model) {
        blogService.deleteBlog(Long.parseLong(blogId));
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}


