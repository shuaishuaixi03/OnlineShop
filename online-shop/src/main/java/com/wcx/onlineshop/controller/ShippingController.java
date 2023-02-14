package com.wcx.onlineshop.controller;


import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.consts.OnlineShopConst;
import com.wcx.onlineshop.form.ShippingForm;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.service.IShippingService;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVO<Map<String, Integer>> add(@Valid @RequestBody ShippingForm shippingForm,
                                                HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return shippingService.add(user.getId(), shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVO delete(@PathVariable Integer shippingId,
                             HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return shippingService.delete(user.getId(), shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVO delete(@Valid @RequestBody ShippingForm shippingForm,
                             @PathVariable Integer shippingId,
                             HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return shippingService.update(user.getId(), shippingId, shippingForm);
    }

    @GetMapping("/shippings")
    public ResponseVO<PageInfo> list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     HttpSession session
                                     ) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return shippingService.list(user.getId(), pageNum, pageSize);
    }


}
