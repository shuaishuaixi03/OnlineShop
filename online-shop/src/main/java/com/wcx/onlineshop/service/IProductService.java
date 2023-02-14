package com.wcx.onlineshop.service;

import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.vo.ProductDetailVO;
import com.wcx.onlineshop.vo.ResponseVO;

public interface IProductService {
    ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVO<ProductDetailVO> detail(Integer productId);
}
