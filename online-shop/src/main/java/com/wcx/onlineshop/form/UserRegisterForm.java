package com.wcx.onlineshop.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserRegisterForm {
//    易混淆的三个注解 NotEmpty、NotBlank、NotNull
//    a.NotNull: 不能为 null，但可以为 empty, 没有 Size 的约束
//    b.NotEmpty : 不能为 null，且 Size>0
//    c.NotBlank: 只用于 String, 不能为 null 且 trim () 之后 size>0
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    UserRegisterForm() {}
}
