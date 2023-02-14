package com.wcx.onlineshop.service.impl;

import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.enums.ResponseEnum;
import com.wcx.onlineshop.enums.RoleEnum;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImplTest extends OnlineShopApplicationTests {

    private final String username = "wcx-test";
    private final String password = "123456";

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Before
    public void register() {
        User user =  new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail("123456@qq.com");
        user.setRole(RoleEnum.ADMIN.getCode());
        userServiceImpl.register(user);
    }

    @Test
    public void login() {
        ResponseVO<User> responseVO = userServiceImpl.login(username, password);
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), responseVO.getStatus());
    }
}