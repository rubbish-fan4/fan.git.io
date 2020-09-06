package com.xin.service;

import com.xin.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //查
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> listTag();
    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
    //存
    Tag saveTag(Tag tag);

    //分页
    Page<Tag> listPage(Pageable pageable);

    //改
    Tag updateTag(Long id,Tag tag);

    //删
    void deleteTag(Long id);
}
