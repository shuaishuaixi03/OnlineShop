package com.wcx.onlineshop.controller;


import com.wcx.onlineshop.service.ICategoryService;
import com.wcx.onlineshop.vo.CategoryVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    //ICategoryService只有一个实现类CategoryServiceImpl，所以用ICategoryService
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVO<List<CategoryVO>> selectAll() {
        return categoryService.selectAll();
    }

}
