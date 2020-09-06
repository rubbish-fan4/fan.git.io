package com.xin.service;

import com.xin.dao.UserRepository;
import com.xin.pojo.User;
import com.xin.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //要实现dao
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        //与数据库的信息进行比较
        //就是这个方法findByUsernameAndPassword他会自动与数据库进行比较
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }
}
