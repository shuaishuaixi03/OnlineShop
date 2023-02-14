package com.wcx.onlineshop.dao;

import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.pojo.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderMapperTest extends OnlineShopApplicationTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByPrimaryKey() {
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(order.toString());
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}