package org.sharon.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Type;
import org.sharon.demo.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-15:18
 */
public interface TypeService {
    public PageInfo<Type> getTypeByPage(int pageId, int pageSize, String orderBy);

    public Type updateType(Type type);

    public Type insertType(Type type);

    public Type deleteType(Long id);

    public Type getTypeById(Long id);

    public Type getTypeByName(String name);

    public List<Type> getAllTypes();

    public List<Type> getTypesSortByBlogs(int limit);
}
