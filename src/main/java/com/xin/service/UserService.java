package com.xin.service;

import com.xin.pojo.User;

public interface UserService {

    //检查登入
    User checkUser(String username,String password);
}
