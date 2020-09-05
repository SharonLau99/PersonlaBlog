package org.sharon.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Blog;
import org.sharon.demo.exception.NotFoundException;
import org.sharon.demo.mapper.BlogMapper;
import org.sharon.demo.mapper.CommentMapper;
import org.sharon.demo.mapper.TagToBlogMapper;
import org.sharon.demo.service.BlogService;
import org.sharon.demo.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author sharon
 * @create 2020-09-01-10:12
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired(required = false)
    BlogMapper blogMapper;

    @Autowired(required = false)
    TagToBlogMapper tagToBlogMapper;

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    public Blog getBlogById(long id) {
        return blogMapper.getBlogById(id);
    }

    //mk的文本需要转成html文本才能在博客详情页正常显示
    @Override
    public Blog getBlogAndConvert(long id) {
        Blog blog = blogMapper.getBlogById(id);
        if (blog == null) {
            //博客不存在，抛异常
            throw new NotFoundException("博客不存在。");
        }
        //从mk转换呈html文本
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新博客views
        blogMapper.updateViews(id);
        return blog;
    }

    //获得最新推荐博客
    @Override
    public List<Blog> getRecommendBlogs(int limit) {
        return blogMapper.getRecommendBlogs(limit);
    }

    //通过标签获得博客
    @Override
    public PageInfo<Blog> getBlogByTag(int pageId, int pageSize, String orderBy, Long tagId) {
        PageHelper.startPage(pageId, pageSize, orderBy);
        List<Blog> blogList = blogMapper.getBlogByTag(tagId);
        if (blogList == null || blogList.size() < 1) {
            throw new NotFoundException("找不到该标签下的博客。");
        }
        return new PageInfo<>(blogList);
    }

    //按照一定条件获得博客
    @Override
    public PageInfo<Blog> getBlogByCondition(int pageId, int pageSize, String orderBy, Blog blog) {
        PageHelper.startPage(pageId, pageSize, orderBy);
        List<Blog> blogList = blogMapper.getBlogByConditions(blog);
        return new PageInfo<>(blogList);
    }

    //获得全部博客
    @Override
    public PageInfo<Blog> getAllBlogs(int pageId, int pageSize, String orderBy) {
        PageHelper.startPage(pageId, pageSize, orderBy);
        List<Blog> blogList = blogMapper.getAllBlogs();
        return new PageInfo<>(blogList);
    }

    //删除博客
    @Transactional
    @Override
    public Blog deleteBlog(long id) {
        commentMapper.deleteComment(id);
        Blog blog = blogMapper.getBlogById(id);
        blogMapper.deleteBlog(id);
        tagToBlogMapper.delete(id);
        return blog;
    }

    //新增博客
    @Transactional
    @Override
    public Blog insertBlog(Blog blog, List<Long> tagIdsList) {
        if (blog == null || blog.getTitle() == null) {
            //blog为空或者标题为空
            return null;
        }

        if (blogMapper.getBlogByTitle(blog.getTitle()) != null) {
            //博客标题不能重复
            return null;
        }
        //标签也要保存
//        System.out.println(blog);
        blogMapper.insertBlog(blog);
        if (tagIdsList != null && tagIdsList.size() >0){
            tagToBlogMapper.insert(blog.getId(), tagIdsList);
        }
        return blog;
    }

    //更新博客
    @Transactional
    @Override
    public Blog updateBlog(Blog blog, List<Long> tagIdsList) {
        if (blog == null || blogMapper.getBlogById(blog.getId()) == null) {
            //blog为空或者id不存在
            System.out.println(blog);
            return null;
        }

        Blog blogByTitle = blogMapper.getBlogByTitle(blog.getTitle());
        if (blogByTitle != null && !blogByTitle.getId().equals(blog.getId())) {
            System.out.println(blogByTitle.getId() + blog.getId());
            //博客标题不能和其他的重复
            return null;
        }

        //标签也要保存
//        System.out.println(blog);
        blog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        blogMapper.updateBlog(blog);
        if (tagIdsList != null && tagIdsList.size() >0){
            //删除旧的标签关系，更新标签关系
            tagToBlogMapper.delete(blog.getId());
            tagToBlogMapper.insert(blog.getId(), tagIdsList);
        }
        return blog;
    }

    //搜索博客
    @Override
    public PageInfo<Blog> getBlogsBySearch(int pageNo, int pageSize, String orderBy, String keywords) {
        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<Blog> blogList = blogMapper.getBlogsBySearch(keywords);
        return new PageInfo<>(blogList);
    }

    //按照不同年份获得blog的linkehashmap
    @Override
    public LinkedHashMap<Long, List<Blog>> getBlogsByYear() {
        LinkedHashMap<Long, List<Blog>> res = new LinkedHashMap<>();
        List<Long> years = blogMapper.getYears();
        for (Long year : years) {
            res.put(year, blogMapper.getBlogByYear(year));
        }
        return res;
    }

    //获得已发布的博客
    @Override
    public Long getPublishedBlogCount() {
        return blogMapper.getPublishedBlogCount();
    }
}
