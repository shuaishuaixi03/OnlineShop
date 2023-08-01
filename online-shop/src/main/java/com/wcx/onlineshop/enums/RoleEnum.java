package com.wcx.onlineshop.enums;

import lombok.Getter;


/**
 * 用户角色枚举类
 */
@Getter
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(0),
    /**
     * 用户
     */
    CUSTOMER(1),
    ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
