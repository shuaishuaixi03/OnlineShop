package com.wcx.onlineshop.vo;

import com.wcx.onlineshop.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wcx
 * @date 2023/8/1 14:43
 */
@Data
public class OrderVO {
    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVO> orderItemVoList;

    private Integer shippingId;

    private Shipping shippingVo;
}
