package com.wcx.pay.service.impl;

import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.wcx.pay.dao.PayInfoMapper;
import com.wcx.pay.enums.PayPlatformEnum;
import com.wcx.pay.pojo.PayInfo;
import com.wcx.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements IPayService {

    private final static String QUEUE_PAY_NOTIFY = "payNotify";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private PayInfoMapper payInfoMapper;


    @Autowired
    private AmqpTemplate amqptemplate;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        //写入数据库
        PayInfo payInfo = new PayInfo(orderId,
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("支付订单demo01");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("payResponse={}", payResponse);
        return payResponse;

    }

    @Override
    public String asynsNotify(String notifyData) {
        //签名验证
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}", payResponse);

        //金额校验（从数据库中查询订单）
        PayInfo payInfo = payInfoMapper.selectByOrderNo(payResponse.getOrderId());
        if (payInfo == null) {
            //TODO 正常情况下payInfo不可能为空，所以此时应该通过钉钉或短信发出告警
            throw new RuntimeException("通过orderNo查询的订单为空");
        }
        //如果订单不是支付成功状态
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            //double类型的数据因精度不好比较，涉及到精度比较采用BigDecimal数据类型
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                //TODO 告警
                throw new RuntimeException("异步通知处理中的金额和数据库的金额不一致, orderNo= " + payInfo.getOrderNo());
            }
            //将订单状态修改成支付成功
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            //设置订单流水号
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfo.setUpdateTime(null);
            //修改数据库中的订单支付状态
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        //pay项目发送MQ消息，mall项目结束MQ消息
        amqptemplate.convertAndSend(QUEUE_PAY_NOTIFY, new Gson().toJson(payInfo));

        //让支付平台已经接收通知，不要额外发送
        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            return  "<xml>\n" +
                    "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            return "success";
        }
        throw new RuntimeException("暂不支持这种支付平台");
    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(orderId);
    }
}
