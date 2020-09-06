package com.xin.service;

import com.xin.dao.TypeRepository;
import com.xin.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryExtensionsKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 继承接口，实现增删改查
 * 注入TypeRepository实现JPA操作数据库
 * @Transactional 事务
 */
@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeName(String name) {
        return typeRepository.getTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =PageRequest.of(0,size,sort);

        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    @Query
    public Type updateType(Long id, Type type) {
        //先查到
        Type t = typeRepository.getTypeById(id);
        if (t == null) {
            return null;    //这里要修改成抛出自定义异常
        }
        //把查到的数据保存，把type的值复制到t中
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }
}
