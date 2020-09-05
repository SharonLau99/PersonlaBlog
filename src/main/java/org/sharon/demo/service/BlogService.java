package org.sharon.demo.service;

import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Blog;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-23:07
 */
public interface BlogService {
    Blog getBlogById(long id);

    Blog getBlogAndConvert(long id);

    List<Blog> getRecommendBlogs(int limit);

    PageInfo<Blog> getBlogByTag(int pageId, int pageSize, String orderBy, Long tagId);

    PageInfo<Blog> getBlogByCondition(int pageId, int pageSize, String orderBy, Blog blog);

    PageInfo<Blog> getAllBlogs(int pageId, int pageSize, String orderBy);

    Blog deleteBlog(long id);

    Blog insertBlog(Blog blog, List<Long> tagIdsList);

    Blog updateBlog(Blog blog, List<Long> tagIdsList);

    PageInfo<Blog> getBlogsBySearch(int pageNo, int pageSize, String orderBy, String keywords);

    LinkedHashMap<Long, List<Blog>> getBlogsByYear();

    Long getPublishedBlogCount();
}
