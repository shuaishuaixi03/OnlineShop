package com.wcx.onlineshop.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.form.CartAddfForm;
import com.wcx.onlineshop.form.CartUpdateForm;
import com.wcx.onlineshop.service.ICartService;
import com.wcx.onlineshop.vo.CartVO;
import com.wcx.onlineshop.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class CartServiceImplTest extends OnlineShopApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddfForm cartAddfForm = new CartAddfForm();
        cartAddfForm.setProductId(29);
        cartAddfForm.setSelected(true);
        ResponseVO<CartVO> cartVOResponseVO = cartService.add(1, cartAddfForm);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void list() {
        ResponseVO<CartVO> cartVOResponseVO = cartService.list(1);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm(1, false);
        ResponseVO<CartVO> cartVOResponseVO = cartService.update(1, 27, cartUpdateForm);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void delete() {
        ResponseVO<CartVO> cartVOResponseVO = cartService.delete(1, 26);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void selectAll() {
        ResponseVO<CartVO> cartVOResponseVO = cartService.selectAll(1);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void unselectAll() {
        ResponseVO<CartVO> cartVOResponseVO = cartService.UnselectAll(1);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }

    @Test
    public void sum() {
        ResponseVO<Integer> cartVOResponseVO = cartService.sum(1);
        log.info("cartVOResponseVO={}", gson.toJson(cartVOResponseVO));
    }
}