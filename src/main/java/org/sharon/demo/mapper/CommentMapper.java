package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sharon.demo.bean.Comment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author sharon
 * @create 2020-09-04-19:04
 */
@Mapper
public interface CommentMapper {

    //获得博客下评论列表
    List<Comment> getParentCommentByBlogId(@Param("blogId")Long blogId);

    //新增一条评论
    @Insert("insert t_comment(avatar, content, email, nickname, blog_id, parent_comment_id, admin_comment) " +
            "values(#{avatar}, #{content}, #{email}, #{nickname}, #{blog.id}, #{parentComment.id}, #{adminComment})")
    void saveComment(Comment comment);

    //删除某条博客下的全部评论
    @Delete("delete from t_comment where parent_comment_id = #{blogId}")
    void deleteComment(@Param("blogId") Long blogId);
}
