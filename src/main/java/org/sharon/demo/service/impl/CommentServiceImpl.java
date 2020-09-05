package org.sharon.demo.service.impl;

import org.sharon.demo.bean.Comment;
import org.sharon.demo.mapper.CommentMapper;
import org.sharon.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sharon
 * @create 2020-09-04-19:56
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired(required = false)
    CommentMapper commentMapper;

    //暂时存储回复的list
    private List<Comment> tempReplys = new ArrayList<>();

    //展示博客时，需要对博客进行处理，分两层，根评论，和跟评论下的所有评论，按照时间升序
    @Override
    public List<Comment> getParentCommentsAndReconstruct(Long blogId) {
        List<Comment> parentCommentByBlogId = commentMapper.getParentCommentByBlogId(blogId);
        List<Comment> res = new ArrayList<>();

        for (Comment comment : parentCommentByBlogId) {
            combineChildren(comment.getReplyComments());
            comment.setReplyComments(tempReplys);
            res.add(comment);
            tempReplys = new ArrayList<>();
        }

        return res;
    }

    public void combineChildren(List<Comment> replyComments) {
        if (replyComments == null || replyComments.size() < 1) return;
        //根节点评论，需要找到它的子评论列表
        for (Comment reply : replyComments) {
            tempReplys.add(reply);
            combineChildren(reply.getReplyComments());
        }
    }


    //保存评论
    @Transactional
    @Override
    public void saveComment(Comment comment) {
        commentMapper.saveComment(comment);
    }
}
