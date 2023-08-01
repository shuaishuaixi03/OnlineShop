package com.wcx.onlineshop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wcx
 * @date 2023/8/1 14:44
 */
@Data
public class OrderItemVO {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}
