package com.wcx.onlineshop.service.impl;

import com.wcx.onlineshop.dao.UserMapper;
import com.wcx.onlineshop.enums.RoleEnum;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.service.IUserService;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.wcx.onlineshop.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVO<User> register(User user) {
        //用户名不可以重复
        if (userMapper.countByUsername(user.getUsername()) > 0) {
            return ResponseVO.error(UESRNAME_EXIST);
        }
        //邮箱不可以重复
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            return ResponseVO.error(EMAIL_EXIST);
        }

        user.setRole(RoleEnum.CUSTOMER.getCode());

        //MD5摘要算法(加密密码)
        DigestUtils.md5DigestAsHex(
                user.getPassword().getBytes(StandardCharsets.UTF_8)
        );
        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVO.error(ERROR);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            // 用户名不存在
            return ResponseVO.error(USERNAME_OR_PASSWORD_ERROR);
        }
        if (user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))
        )) {
            // 密码错误
            return ResponseVO.error(USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVO.successByData(user);
    }
}
