package com.wcx.onlineshop.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wcx
 * @date 2023/8/1 14:43
 */
@Data
public class OrderCreateForm {
    @NotNull
    private Integer shippingId;
}
