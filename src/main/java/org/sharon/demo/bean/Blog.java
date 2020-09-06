package org.sharon.demo.bean;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Blog实体类
 * @author sharon
 * @create 2020-08-29-22:10
 */
@Component
public class Blog {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    //懒加载，要用到的时候才加载
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private String firstPicture;
    private String description;
    private Integer views;
    private String flag;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean recommend;
    private boolean published;
    private Timestamp createTime;
    private Timestamp updateTime;

    private Type type;

    private List<Tag> tags = new ArrayList<>();
    private String tagIds;

    private User user;

    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        tagIdsInit();
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        tagIdsInit();
        this.tagIds = tagIds;
    }

    public void tagIdsInit() {
        if (tags != null && tags.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    stringBuilder.append(",");
                } else {
                    flag = true;
                }
                stringBuilder.append(tag.getId());
            }
            tagIds = stringBuilder.toString();
        }
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", flag='" + flag + '\'' +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", recommend=" + recommend +
                ", published=" + published +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", tagIds='" + tagIds + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
