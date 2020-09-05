package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.*;
import org.sharon.demo.bean.Tag;
import org.sharon.demo.bean.Type;

import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-21:33
 */
@Mapper
public interface TagMapper {
    //获得全部标签
    @Select("select * from t_tag")
    public List<Tag> getAllTags();

    //通过id获得标签
    @Select("select * from t_tag where id = #{id}")
    public Tag getTagById(Long id);

    //通过标签名获得标签
    @Select("select * from t_tag where name = #{name}")
    public Tag getTagByName(String name);

    //更新标签
    @Update("update t_tag set name = #{name} where id = #{id}")
    public void updateTag(Tag tag);

    //删除标签
    @Delete("delete from t_tag where id = #{id}")
    public void deleteTag(Long id);

    //新增标签
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert t_tag(name) values(#{name})")
    public void insertTag(Tag tag);

    //通过一系列标签id获得标签list
    public List<Tag> getTagsByIdList(List<Long> tagIds);

    //根据相关联博客数量获得标签列表（按照相关博客数量降序）
    @Select("select tag.id, tag.name, count(t.blog_id) blogs from t_blog_tags t left join t_tag tag on tag.id = t.tag_id group by t.tag_id order by count(t.blog_id) desc limit #{limit}")
    public List<Tag> getTagsSortByBlogs(@Param("limit") int limit);
}
