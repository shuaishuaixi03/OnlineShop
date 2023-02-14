package com.wcx.onlineshop.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1, "服务端异常"),
    SUCCESS(0, "操作成功"),
    PASSWORD_ERRORS(1, "密码错误"),
    UESRNAME_EXIST(2, "用户名已经存在"),

    PARAM_ERROR(3, "参数错误"),

    EMAIL_EXIST(4, "邮箱已经存在"),

    NEED_LOGIN(10, "用户未登录，请先登录"),

    USERNAME_OR_PASSWORD_ERROR(11, "用户名或者密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "商品已下架或删除"),

    PRODUCT_NOT_EXIST(13, "商品不存在"),

    PRODUCT_STOCK_ERROR(14, "商品数量错误"),

    CART_PRODUCT_NOT_EXIST(14, "购物车中的商品不存在"),

    DELETE_SHIPPING_ERROR(15, "删除收货地址失败")

    ;
    private Integer status;
    private String msg;

    ResponseEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
