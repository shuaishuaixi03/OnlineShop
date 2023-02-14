package com.wcx.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.wcx.pay.pojo.PayInfo;

import java.math.BigDecimal;

public interface IPayService {
    /**
     * 创建支付
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);


    /**
     * 异步通知处理
     *
     * @return
     */
    String asynsNotify(String notifyData);

    /**
     * 通过支付订单号查询支付订单
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}
