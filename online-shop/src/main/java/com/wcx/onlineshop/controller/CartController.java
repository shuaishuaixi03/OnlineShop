package com.wcx.onlineshop.controller;

import com.wcx.onlineshop.consts.OnlineShopConst;
import com.wcx.onlineshop.form.CartAddfForm;
import com.wcx.onlineshop.form.CartUpdateForm;
import com.wcx.onlineshop.pojo.User;
import com.wcx.onlineshop.service.ICartService;
import com.wcx.onlineshop.vo.CartVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * 购物车模块
 */
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;


    @GetMapping("/carts")
    public ResponseVO<CartVO> list(HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddfForm cartAddfForm,
                                      HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PutMapping("/carts/{productId}")
    public ResponseVO<CartVO> delete(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                     HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVO<CartVO> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                     HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVO<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVO<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.UnselectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVO<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(OnlineShopConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }

}
