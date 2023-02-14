package com.wcx.onlineshop.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.enums.ResponseEnum;
import com.wcx.onlineshop.form.ShippingForm;
import com.wcx.onlineshop.service.IShippingService;
import com.wcx.onlineshop.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Slf4j
public class ShippingServiceImplTest extends OnlineShopApplicationTests {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer uid = 1;

    private ShippingForm shippingForm;


    @Before
    public void before() {
        ShippingForm shippingForm = new ShippingForm(
                "wcx",
                "150",
                "150",
                "湖北",
                "孝感市",
                "孝昌县",
                "某某小区",
                "1000");
        this.shippingForm = shippingForm;
    }

    @Autowired
    private IShippingService shippingService;

    @Test
    public void add() {
        ResponseVO<Map<String, Integer>> add = shippingService.add(uid, shippingForm);
        log.info("【添加收货地址】 res = {}", gson.toJson(add));
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), add.getStatus());
    }

    @Test
    public void delete() {
        ResponseVO delete = shippingService.delete(20, 2022);
        log.info("【删除收货地址】 res = {}", gson.toJson(delete));
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), delete.getStatus());
    }

    @Test
    public void update() {
        shippingForm.setReceiverName("汪成肸");
        ResponseVO update = shippingService.update(uid, 4, shippingForm);
        log.info("【更新收货地址】 res = {}", gson.toJson(update));
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), update.getStatus());
    }

    @Test
    public void list() {
        ResponseVO<PageInfo> list = shippingService.list(uid, 1, 10);
        log.info("【获取收货地址列表】 res = {}", gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), list.getStatus());
    }
}