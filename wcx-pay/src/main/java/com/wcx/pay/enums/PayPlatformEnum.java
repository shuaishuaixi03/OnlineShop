package com.wcx.pay.enums;


import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;


/**
 * 支付平台枚举类
 */
@Getter
public enum PayPlatformEnum {
    /**
     * 支付宝
     */
    ALIPAY(1),
    /**
     * 微信
     */
    WX(2),
    ;
    Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (payPlatformEnum.name().equals(bestPayTypeEnum.getPlatform().name())) {
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("错误的支付平台" + bestPayTypeEnum.name());
    }
}
