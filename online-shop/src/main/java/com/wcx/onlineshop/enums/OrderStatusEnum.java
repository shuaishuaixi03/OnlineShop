package com.wcx.onlineshop.enums;

import lombok.Getter;

/**
 * @author wcx
 * @date 2023/8/1 15:14
 */
@Getter
public enum OrderStatusEnum {
    CANCELED(0, "已取消"),

    NO_PAY(10, "未付款"),

    PAID(20, "已付款"),

    SHIPPED(40, "已发货"),

    TRADE_SUCCESS(50, "交易成功"),

    TRADE_CLOSE(60, "交易关闭"),
            ;

    Integer code;

    String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
