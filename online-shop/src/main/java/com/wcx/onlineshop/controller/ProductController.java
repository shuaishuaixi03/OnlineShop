package com.wcx.onlineshop.controller;


import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.service.IProductService;
import com.wcx.onlineshop.vo.ProductDetailVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品模块
 */
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVO<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize
                                     ) {
        return productService.list(categoryId, pageNum, pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVO<ProductDetailVO> detail(@PathVariable Integer productId) {
        return productService.detail(productId);
    }
}
