package com.wcx.onlineshop.enums;

import lombok.Getter;

/**
 * @author wcx
 * @date 2023/8/1 15:14
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    ;

    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
