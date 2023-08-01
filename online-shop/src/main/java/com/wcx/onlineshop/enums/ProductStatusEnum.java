package com.wcx.onlineshop.enums;

import lombok.Getter;


/**
 * 商品状态枚举类
 */
@Getter
public enum ProductStatusEnum {
    /**
     * 在售状态
     */
    ON_SALE(1),
    /**
     * 下架状态
     */
    OFF_SALE(2),
    /**
     * 删除状态
     */
    DELETE(3)
    ;
    Integer code;
    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
