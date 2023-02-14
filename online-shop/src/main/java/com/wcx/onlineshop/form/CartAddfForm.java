package com.wcx.onlineshop.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddfForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;

}
