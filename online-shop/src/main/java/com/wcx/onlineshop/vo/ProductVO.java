package com.wcx.onlineshop.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVO {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;


}
