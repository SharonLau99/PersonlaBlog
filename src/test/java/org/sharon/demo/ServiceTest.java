package org.sharon.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharon.demo.bean.Comment;
import org.sharon.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author sharon
 * @create 2020-09-04-20:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    public void test1() {
        List<Comment> parentCommentsAndReconstruct = commentService.getParentCommentsAndReconstruct(27L);
        for (Comment c : parentCommentsAndReconstruct) {
            System.out.println(c);
        }
    }

    @Test
    public void test2() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }
}
