package com.wcx.onlineshop.service.impl;

import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.enums.ResponseEnum;
import com.wcx.onlineshop.service.IProductService;
import com.wcx.onlineshop.vo.ProductDetailVO;
import com.wcx.onlineshop.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ProductServiceImplTest extends OnlineShopApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVO<PageInfo> listResponseVO = productService.list(null, 1, 1);
        log.info("listResponseVO={}", listResponseVO);
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), listResponseVO.getStatus());
    }

    @Test
    public void test() {
        ResponseVO<ProductDetailVO> productDetailVOResponseVO = productService.detail(26);
        log.info("productDetailVOResponseVO={}", productDetailVOResponseVO);
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), productDetailVOResponseVO.getStatus());
    }
}