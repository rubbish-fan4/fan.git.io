package com.xin.dao;

import com.xin.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA处理数据库
 * <User,Long>  操作类型与主键类型
 */
public interface UserRepository extends JpaRepository<User,Long> {
    //定义方法直接使用,符合规则就可以
    User findByUsernameAndPassword(String name, String password);
}
