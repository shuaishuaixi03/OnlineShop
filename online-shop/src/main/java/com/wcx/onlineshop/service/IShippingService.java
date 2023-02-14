package com.wcx.onlineshop.service;

import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.form.ShippingForm;
import com.wcx.onlineshop.vo.ResponseVO;

import java.util.Map;

public interface IShippingService {

    ResponseVO<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVO delete(Integer uid, Integer shippingId);

    ResponseVO update(Integer uid, Integer shippingId, ShippingForm shippingForm);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

}
