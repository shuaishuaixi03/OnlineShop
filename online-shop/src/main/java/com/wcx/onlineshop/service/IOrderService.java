package com.wcx.onlineshop.service;

import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.vo.OrderVO;
import com.wcx.onlineshop.vo.ResponseVO;

public interface IOrderService {
    ResponseVO<OrderVO> create(Integer uid, Integer shippingId);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVO<OrderVO> detail(Integer uid, Long orderNo);

    ResponseVO cancel(Integer uid, Long orderNo);

    void paid(Long orderNo);
}
