package org.sharon.demo.service;

import org.sharon.demo.bean.Comment;

import java.util.List;

/**
 * @author sharon
 * @create 2020-09-04-19:52
 */
public interface CommentService {

    public List<Comment> getParentCommentsAndReconstruct(Long blogId);

    public void saveComment(Comment comment);
}
