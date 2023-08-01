package com.wcx.onlineshop.interceptor;

import com.wcx.onlineshop.consts.OnlineShopConst;
import com.wcx.onlineshop.exception.InvalidUserException;
import com.wcx.onlineshop.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 处理判断用户是否登录的拦截器
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(OnlineShopConst.CURRENT_USER);
        if (user == null) {
            log.info("user=null");
            throw new InvalidUserException();
        }
        return true;
    }
}
