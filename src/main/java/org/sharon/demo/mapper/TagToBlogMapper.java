package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sharon
 * @create 2020-09-01-21:15
 */
@Mapper
public interface TagToBlogMapper {
    //在标签-博客表中新增关系
    void insert(@Param("blogId") Long blogId, @Param("tagIds") List<Long> tagIds);

    //删除关系
    @Delete("delete from t_blog_tags where blog_id = #{blogId}")
    void delete(@Param("blogId") Long blogId);
}
