package com.xin.dao;

import com.xin.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag getTagById(Long id);
    Tag getTagByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);
}
