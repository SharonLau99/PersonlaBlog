package org.sharon.demo.bean;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章分类实体类
 * @author sharon
 * @create 2020-08-29-22:18
 */
@Component
public class Type {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long blogs;

    public Type() {
    }

    public Type(Long id) {
        this.id = id;
    }

    public Long getBlogs() {
        return blogs;
    }

    public void setBlogs(Long blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
