package com.xin.service;

import com.xin.dao.TagRepository;
import com.xin.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {   //1,2,3
        return tagRepository.findAllById(converTolist(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    //把字符串转换成数组
    private List<Long> converTolist(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idarrary = ids.split(",");
            for (int i = 0;i < idarrary.length; i ++){
                list.add(new Long(idarrary[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Page<Tag> listPage(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Query
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getTagById(id);
        if (t == null){
            return null;    //这里要返回自己创建的异常处理
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
       tagRepository.deleteById(id);
    }
}
