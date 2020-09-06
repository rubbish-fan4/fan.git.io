package com.xin.dao;

import com.xin.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * JpaSpecificationExecutor实现组合查询
 */
public interface BlogsRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    Blog getBlogById(Long id);

    @Query("select b from t_blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    //博客浏览次数加1
    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //查询博客的所有年份
    @Query("select function('date_format',b.updateTime,'%Y') as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc ")
    List<String> findGroupYear();
    //按照年份查询博客
    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') = ?1 ")
    List<Blog> findByYear(String year);
}
