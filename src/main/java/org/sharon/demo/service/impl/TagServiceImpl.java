package org.sharon.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Tag;
import org.sharon.demo.bean.Type;
import org.sharon.demo.mapper.TagMapper;
import org.sharon.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-21:57
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired(required = false)
    TagMapper tagMapper;

    //全部标签分页列表
    @Override
    public PageInfo<Tag> getTagByPage(int pageId, int pageSize, String orderBy) {
        PageHelper.startPage(pageId, pageSize, orderBy);
        List<Tag> tagList = tagMapper.getAllTags();
        return new PageInfo<>(tagList);
    }

    //更新标签
    @Transactional
    @Override
    public Tag updateTag(Tag tag) {
        if (tag == null || tagMapper.getTagById(tag.getId()) == null) {
            //tag为空或者id不存在
            return null;
        }
        tagMapper.updateTag(tag);
        return tag;
    }

    //新增标签
    @Transactional
    @Override
    public Tag insertTag(Tag tag) {
        if (tagMapper.getTagByName(tag.getName()) != null) {
            return null;
        }
        tagMapper.insertTag(tag);
        return tag;
    }

    //删除标签
    @Transactional
    @Override
    public Tag deleteTag(Long id) {
        Tag tag = tagMapper.getTagById(id);
        tagMapper.deleteTag(id);
        return tag;
    }

    //获得标签（by id）
    @Override
    public Tag getTagById(Long id) {
        return tagMapper.getTagById(id);
    }

    //通过名字获得标签
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    //获得全部标签
    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    //辅助方法
    @Override
    public List<Long> convertToList(String tagIds) {
        List<Long> res = new ArrayList<>();
        if (!"".equals(tagIds) && tagIds != null) {
            String[] split = tagIds.split(",");
            for (int i = 0; i < split.length; i++) {
                //利用正则表达式判断是否是id，是字符串，默认tag都不会是纯数字
                if (split[i].matches("^[1-9][0-9]*$")) {
                    res.add(Long.parseLong(split[i]));
                } else {
                    //允许自定义标签并增加
                    Tag tag = new Tag(split[i]);
                    try {
                        //新增标签，如果标签名称不能重复，如果冲突，会抛出异常并处理
                        tagMapper.insertTag(tag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tag.getId() == null) {
                        tag = tagMapper.getTagByName(split[i]);
                    }
                    res.add(tag.getId());
                }
            }
        }
        return res;
    }

    @Override
    public List<Tag> getTagFromList(List<Long> tagIds) {
        if (tagIds == null || tagIds.size() < 1) return null;
        return tagMapper.getTagsByIdList(tagIds);
    }

    @Override
    public List<Tag> getTagSortByBlogs(int limit) {
        return tagMapper.getTagsSortByBlogs(limit);
    }
}
