package com.wcx.onlineshop.controller;

import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.consts.OnlineShopConst;
import com.wcx.onlineshop.form.OrderCreateForm;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.service.IOrderService;
import com.wcx.onlineshop.vo.OrderVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 *
 * 订单模块
 * @author wcx
 * @date 2023/8/1 14:37
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVO<OrderVO> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVO<PageInfo> list(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> detail(@PathVariable Long orderNo,
                                      HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVO cancel(@PathVariable Long orderNo,
                             HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }

}
