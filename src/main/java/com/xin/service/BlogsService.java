package com.xin.service;

import com.xin.pojo.Blog;
import com.xin.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface    BlogsService {
    //查
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
    Blog getBlogById(Long id);
    List<Blog> listRecommendBlogTop(Integer size);
    //获得每个年份下的博客
    Map<String,List<Blog>> archiveBlog();
    //获取博客的数量
    Long countBlog();
    //分页
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(String query,Pageable pageable);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    //存
    Blog saveBlog(Blog blog);

    //改
    Blog updateBlog(Long id,Blog blog);

    //删除
    void deleteBlog(Long id);
}
