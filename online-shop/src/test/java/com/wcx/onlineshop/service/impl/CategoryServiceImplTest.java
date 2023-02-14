package com.wcx.onlineshop.service.impl;

import com.wcx.onlineshop.OnlineShopApplicationTests;
import com.wcx.onlineshop.enums.ResponseEnum;
import com.wcx.onlineshop.service.ICategoryService;
import com.wcx.onlineshop.vo.CategoryVO;
import com.wcx.onlineshop.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public class CategoryServiceImplTest extends OnlineShopApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVO<List<CategoryVO>> listResponseVO = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getStatus(), listResponseVO.getStatus());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001, set);
        log.info("set={}", set);
    }
}