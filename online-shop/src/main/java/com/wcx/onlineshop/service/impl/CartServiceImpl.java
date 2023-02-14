package com.wcx.onlineshop.service.impl;

import com.google.gson.Gson;
import com.wcx.onlineshop.dao.ProductMapper;
import com.wcx.onlineshop.enums.ProductStatusEnum;
import com.wcx.onlineshop.form.CartAddfForm;
import com.wcx.onlineshop.form.CartUpdateForm;
import com.wcx.onlineshop.pojo.Cart;
import com.wcx.onlineshop.pojo.Product;
import com.wcx.onlineshop.service.ICartService;
import com.wcx.onlineshop.vo.CartProductVO;
import com.wcx.onlineshop.vo.CartVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.wcx.onlineshop.consts.OnlineShopConst.CART_REDIS_KEY_TEMPLATE;
import static com.wcx.onlineshop.enums.ResponseEnum.*;

@Service
public class CartServiceImpl implements ICartService {

    private Gson gson = new Gson();
    
    
    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Override
    public ResponseVO<CartVO> add(Integer uid, CartAddfForm cartAddfForm) {

        //新增商品数量
        Integer quantity = 1;

        //查询商品
        Product product = productMapper.selectByPrimaryKey(cartAddfForm.getProductId());

        //商品是否存在
        if (product == null) {
            return ResponseVO.error(PRODUCT_NOT_EXIST);
        }

        //商品是否正常销售(没有被下架或删除)
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVO.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品的库存数量够不够
        if (product.getStock() <= 0) {
            return ResponseVO.error(PRODUCT_STOCK_ERROR);
        }


        //写入Redis中
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = hashOperations.get(redisKey, String.valueOf(product.getId()));

        Cart cart = new Cart();
        if (ObjectUtils.isEmpty(value)) {
            //商品不在购物车，新增商品
            cart = new Cart(product.getId(), quantity, cartAddfForm.getSelected());
        } else {
            //商品在购物车中，商品数量加quantity
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        hashOperations.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> list(Integer uid) {
        //取出购物车中选中的商品
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = hashOperations.entries(redisKey);
        List<Cart> cartList = new ArrayList<>();
        List<Integer> productIdList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), Cart.class));
            productIdList.add(Integer.valueOf(entry.getKey()));
        }
        List<Product> productList = productMapper.selectByProductIdList(productIdList);


        Boolean selectedAll = true;
        BigDecimal cartTotalPrice = new BigDecimal(0.0);
        Integer cartTotalQuantity = 0;

        List<CartProductVO> cartProductVOList = new ArrayList<>();
        Iterator<Cart> cartIterator = cartList.iterator();
        Iterator<Product> productIterator = productList.iterator();
        while (cartIterator.hasNext() && productIterator.hasNext()) {
            Product product = productIterator.next();
            Cart cart = cartIterator.next();
            CartProductVO cartProductVO = new CartProductVO(
                    product.getId(),
                    cart.getQuantity(),
                    product.getName(),
                    product.getSubtitle(),
                    product.getMainImage(),
                    product.getPrice(),
                    product.getStatus(),
                    product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                    product.getStock(),
                    cart.getProductSelected()
            );
            if (!cartProductVO.getProductSelected()) {
                selectedAll = false;
            }
            if (cartProductVO.getProductSelected()) {
                cartTotalPrice = cartTotalPrice.add(cartProductVO.getProductTotalPrice());
            }
            cartTotalQuantity += cart.getQuantity();
            cartProductVOList.add(cartProductVO);
        }

        CartVO cartVO = new CartVO();
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVO.successByData(cartVO);
    }

    @Override
    public ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = hashOperations.get(redisKey, String.valueOf(productId));


        if (ObjectUtils.isEmpty(value)) {
            //商品不在购物车，
            return ResponseVO.error(CART_PRODUCT_NOT_EXIST);
        }
        Cart cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        hashOperations.put(redisKey, String.valueOf(productId), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = hashOperations.get(redisKey, String.valueOf(productId));


        if (ObjectUtils.isEmpty(value)) {
            //商品不在购物车
            return ResponseVO.error(CART_PRODUCT_NOT_EXIST);
        }

        hashOperations.delete(redisKey, String.valueOf(productId));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> selectAll(Integer uid) {
        return selectAllORUnselectAll(uid, true);
    }

    @Override
    public ResponseVO<CartVO> UnselectAll(Integer uid) {
        return selectAllORUnselectAll(uid, false);
    }

    @Override
    public ResponseVO<Integer> sum(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Map<String, String> entries = hashOperations.entries(redisKey);

        Integer sum = 0;

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            sum += cart.getQuantity();
        }
        return ResponseVO.successByData(sum);
    }

    private ResponseVO<CartVO> selectAllORUnselectAll(Integer uid, Boolean target) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Map<String, String> entries = hashOperations.entries(redisKey);

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            cart.setProductSelected(target);
            hashOperations.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }


}
