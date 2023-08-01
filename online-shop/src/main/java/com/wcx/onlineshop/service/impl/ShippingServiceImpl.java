package com.wcx.onlineshop.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.dao.ShippingMapper;
import com.wcx.onlineshop.enums.ResponseEnum;
import com.wcx.onlineshop.form.ShippingForm;
import com.wcx.onlineshop.pojo.Shipping;
import com.wcx.onlineshop.service.IShippingService;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVO<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(uid);

        //在对应的xml文件配置insert方法返回自增后的id （加上useGeneratedKeys="true" keyProperty="id"）
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId", shipping.getId());

        return ResponseVO.successByData(map);
    }

    @Override
    public ResponseVO delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByUidAndShippingId(uid, shippingId);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVO.successByMsg("删除收货地址成功");
    }

    @Override
    public ResponseVO update(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        return ResponseVO.successByMsg("更新收货地址成功");
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(shippingList);
        return ResponseVO.successByData(pageInfo);
    }
}
