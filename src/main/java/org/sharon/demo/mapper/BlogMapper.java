package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.*;
import org.sharon.demo.bean.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.GeneratedValue;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sharon
 * @create 2020-09-01-9:05
 */
@Mapper
public interface BlogMapper {
    //通过blog的id获得blog
    Blog getBlogById(Long id);

    //通过标题获取（=）
    Blog getBlogByTitle(@Param("title") String title);

    //获得符合条件的博客列表
    List<Blog> getBlogByConditions(Blog blog);

    //获得全部博客列表
    List<Blog> getAllBlogs();

    //通过标签id获得博客
    List<Blog> getBlogByTag(@Param("tagId") Long tagId);

    //获得最新推荐博客列表
    List<Blog> getRecommendBlogs(@Param("limit") int limit);

    //通过搜索条件获得博客列表
    List<Blog> getBlogsBySearch(@Param("keywords") String keywords);

    //通过更新年份获得博客列表
    List<Blog> getBlogByYear(@Param("year")Long year);

    //获得发布的博客列表
    @Select("select count(id) from t_blog where published = true")
    Long getPublishedBlogCount();

    //获得全部年份
    List<Long> getYears();

    //新增一条博客
    @Insert("insert t_blog(title, content, first_picture, published, flag, " +
            "appreciation, share_statement, commentabled, recommend, type_id, user_id, description)" +
            "values(#{title}, #{content}, #{firstPicture}, #{published}, #{flag}, #{appreciation}," +
            "#{shareStatement}, #{commentabled}, #{recommend}, #{type.id}, #{user.id}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertBlog(Blog blog);

    //更新一条博客
    @Update("update t_blog set title = #{title}, content = #{content}, first_picture = #{firstPicture}, published = #{published}, " +
            "flag = #{flag}, appreciation = #{appreciation}, share_statement = #{shareStatement}, commentabled = #{commentabled}, " +
            "recommend = #{recommend}, type_id = #{type.id}, user_id = #{user.id}, update_time = #{updateTime} where id = #{id}")
    void updateBlog(Blog blog);

    //删除一条博客
    @Delete("delete from t_blog where id = #{id}")
    void deleteBlog(Long id);

    //获得blog实体类的辅助方法
    Type selectType(Long id);

    Tag selectTag(Long id);

    User selectUser(Long id);

    //更新博客的浏览量
    @Transactional
    @Update("update t_blog b set b.views = b.views + 1 where id = #{id}")
    void updateViews(Long id);

}
