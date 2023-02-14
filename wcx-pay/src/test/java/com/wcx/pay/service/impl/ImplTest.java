package com.wcx.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.wcx.WcxPayApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ImplTest extends WcxPayApplicationTests {

    @Autowired
    private PayServiceImpl payServiceImpl;

    @Test
    public void create() {
        //new BigDecimal(0.01)精度会出问题
        payServiceImpl.create("wcx-123456123456", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }
}