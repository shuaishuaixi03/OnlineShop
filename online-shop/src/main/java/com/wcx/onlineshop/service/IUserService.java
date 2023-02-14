package com.wcx.onlineshop.service;

import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.vo.ResponseVO;

public interface IUserService {
    /**
     * 用户注册
     */
    ResponseVO<User> register(User user);

    /**
     * 用户登录
    */
    ResponseVO<User> login(String username, String password);
}
