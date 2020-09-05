package org.sharon.demo.controller;

import org.sharon.demo.bean.Comment;
import org.sharon.demo.bean.MyUserDetails;
import org.sharon.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * 评论controller
 * @author sharon
 * @create 2020-09-04-16:34
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    //游客默认的头像
    String avatar = "/images/user1.jpg";

    @GetMapping("/comments/{id}")
    public String getComments(@PathVariable("id") Long id, Model model) {
        //获取这条博客的评论List
        model.addAttribute("comments", commentService.getParentCommentsAndReconstruct(id));
        return "blog :: commentList";
    }

    @PostMapping("/comment")
    public String postComment(Comment comment, Model model, Principal principal) {
        Long id = comment.getBlog().getId();
        //如果是根评论，需要手动设置父节点为null
        if (comment.getParentComment().getId() == -1) {
            comment.setParentComment(null);
        }
        comment.setAvatar(avatar);

        //判断用户权限，如果是管理员，评论将在前端展示页面显示管理员标签
        if (principal != null) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Collection<GrantedAuthority> authorities = authenticationToken.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    comment.setAdminComment(true);
                }
            }
        }
        //保存并局部更新页面
        commentService.saveComment(comment);
        model.addAttribute("comments", commentService.getParentCommentsAndReconstruct(id));
        return "blog :: commentList";
    }
}
