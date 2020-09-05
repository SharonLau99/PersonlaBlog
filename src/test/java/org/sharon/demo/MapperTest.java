package org.sharon.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharon.demo.bean.*;
import org.sharon.demo.mapper.BlogMapper;
import org.sharon.demo.mapper.CommentMapper;
import org.sharon.demo.mapper.TagMapper;
import org.sharon.demo.mapper.TypeMapper;
import org.sharon.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sharon
 * @create 2020-08-30-10:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Autowired(required = false)
    UserService userService;

    @Autowired(required = false)
    TagMapper tagMapper;

    @Autowired(required = false)
    TypeMapper typeMapper;

    @Autowired(required = false)
    BlogMapper blogMapper;

    @Autowired(required = false)
    CommentMapper commentMapper;
    @Test
    public void getUser() {
        User user = userService.getUser("admin", "Liuxiwen77");
        System.out.println(user);
    }


    @Test
    public void getTagsByBlogs() {
        List<Tag> tagsSortByBlogs = tagMapper.getTagsSortByBlogs(5);
        for (Tag tag : tagsSortByBlogs) {
            System.out.println(tag);
        }
    }

    @Test
    public void getTypeByBlogs() {
        List<Type> typesSortByBlogs = typeMapper.getTypesSortByBlogs(5);
        for (Type type : typesSortByBlogs) {
            System.out.println(type);
        }
    }

    @Test
    public void getRecommendBlog(){
        List<Blog> recommendBlogs = blogMapper.getRecommendBlogs(3);
        for (Blog blog : recommendBlogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void getBlogByTag() {
        List<Blog> blogByTag = blogMapper.getBlogByTag((long) 5);
        for (Blog b : blogByTag) {
            System.out.println(b);
        }
    }

    @Test
    public void getBlogByYear() {
        List<Long> years = blogMapper.getYears();
        for (Long year : years) {
            System.out.println(year);
        }
    }

    @Test
    public void getComment(){
        List<Comment> commentByBlogId = commentMapper.getParentCommentByBlogId(Long.valueOf(27));
        for (Comment c : commentByBlogId) {
            System.out.println(c);
        }
    }
}
