package org.sharon.demo.service;

import com.github.pagehelper.PageInfo;
import org.sharon.demo.bean.Tag;
import org.sharon.demo.bean.Type;

import java.util.List;

/**
 * @author sharon
 * @create 2020-08-31-21:57
 */
public interface TagService {
    public PageInfo<Tag> getTagByPage(int pageId, int pageSize, String orderBy);

    public Tag updateTag(Tag tag);

    public Tag insertTag(Tag tag);

    public Tag deleteTag(Long id);

    public Tag getTagById(Long id);

    public Tag getTagByName(String name);

    public List<Tag> getAllTags();

    public List<Long> convertToList(String tagIds);

    public List<Tag> getTagFromList(List<Long> tagIds);

    public List<Tag> getTagSortByBlogs(int limit);
}
