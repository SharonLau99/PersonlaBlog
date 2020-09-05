package org.sharon.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Type;
import org.sharon.demo.mapper.TypeMapper;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-15:25
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired(required = false)
    TypeMapper typeMapper;

    //全部分类分页列表
    public PageInfo<Type> getTypeByPage(int pageId, int pageSize, String orderBy) {
        PageHelper.startPage(pageId, pageSize, orderBy);
        List<Type> typeList = typeMapper.getAllTypes();
        return new PageInfo<>(typeList);
    }

    //更新分类
    @Override
    public Type updateType(Type type) {
        typeMapper.updateType(type);
        return type;
    }

    //新增分类
    @Override
    public Type insertType(Type type) {
        if (typeMapper.getTypeByName(type.getName()) != null) {
            return null;
        }
        typeMapper.insertType(type);
        return type;
    }

    //删除分类
    @Override
    public Type deleteType(Long id) {
        typeMapper.deleteType(id);
        return null;
    }

    //id获得type
    @Override
    public Type getTypeById(Long id) {
        return typeMapper.getTypeById(id);
    }

    //name获得type
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    //全部分类列表
    @Override
    public List<Type> getAllTypes() {
        return typeMapper.getAllTypes();
    }

    @Override
    public List<Type> getTypesSortByBlogs(int limit) {
        return typeMapper.getTypesSortByBlogs(limit);
    }
}
