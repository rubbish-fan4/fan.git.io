package com.xin.service;

import com.xin.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 分类的增删改查
 */
public interface TypeService {

    //存
    Type saveType(Type type);
    //查
    Type getType(Long id);
    Type getTypeName(String name);
    List<Type> listType();
    List<Type> listTypeTop(Integer integer);
    //分页
    Page<Type> listType(Pageable pageable);
    //删
    void deleteType(Long id);
    //改
    Type updateType(Long id,Type type);
}
