package com.wcx.onlineshop.controller;


import com.wcx.onlineshop.consts.OnlineShopConst;
import com.wcx.onlineshop.form.UserLoginForm;
import com.wcx.onlineshop.form.UserRegisterForm;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.service.IUserService;
import com.wcx.onlineshop.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户模块
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/user/register")
    public ResponseVO<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession session) {
        ResponseVO<User> userResponseVO = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置Session
        session.setAttribute(OnlineShopConst.CURRENT_USER, userResponseVO.getData());
        return userResponseVO;
    }

    @GetMapping("/user")
    public ResponseVO<User> userInfo(HttpSession session) {
        log.info("/user SessionId={}", session.getId());
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return ResponseVO.successByData(user);

    }

    @PostMapping("/user/logout")
    public ResponseVO<User> logout(HttpSession session) {
        log.info("/user/logout SessionId={}", session.getId());
        session.removeAttribute(OnlineShopConst.CURRENT_USER);
        return ResponseVO.success();
    }
}
