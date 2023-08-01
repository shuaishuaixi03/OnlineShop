package com.wcx.onlineshop.service;

import com.wcx.onlineshop.form.CartAddfForm;
import com.wcx.onlineshop.form.CartUpdateForm;
import com.wcx.onlineshop.pojo.Cart;
import com.wcx.onlineshop.vo.CartVO;
import com.wcx.onlineshop.vo.ResponseVO;

import java.util.List;

public interface ICartService {

    ResponseVO<CartVO> add(Integer uid, CartAddfForm cartAddfForm);

    ResponseVO<CartVO> list(Integer uid);

    ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVO<CartVO> delete(Integer uid, Integer productId);

    ResponseVO<CartVO> selectAll(Integer uid);

    ResponseVO<CartVO> UnselectAll(Integer uid);

    ResponseVO<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);

}
