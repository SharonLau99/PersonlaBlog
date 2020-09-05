package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.*;
import org.sharon.demo.bean.Type;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-14:35
 */
@Mapper
public interface TypeMapper {

    //获得全部分类
    @Select("select * from t_type")
    public List<Type> getAllTypes();

    //通过id获得分类
    @Select("select * from t_type where id = #{id}")
    public Type getTypeById(Long id);

    //通过分类名获得分类
    @Select("select * from t_type where name = #{name}")
    public Type getTypeByName(String name);

    //更新分类
    @Update("update t_type set name = #{name} where id = #{id}")
    public void updateType(Type type);

    //删除分类
    @Delete("delete from t_type where id = #{id}")
    public void deleteType(Long id);

    //新增分类
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert t_type(name) values(#{name})")
    public void insertType(Type type);

    //根据相关联博客数量获得分类列表（按照相关博客数量降序）
    @Select("select type.id, type.name, count(blog.id) blogs from t_blog blog left join t_type type on blog.type_id = type.id group by blog.type_id order by count(blog.id) desc limit #{limit}")
    public List<Type> getTypesSortByBlogs(@Param("limit")int limit);
}
