package com.wcx.onlineshop.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcx.onlineshop.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

    private Integer status;

    private String msg;

    private T data;

    public ResponseVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseVO<T> successByMsg(String msg) {
        return new ResponseVO(ResponseEnum.SUCCESS.getStatus(), msg);
    }
    public static <T> ResponseVO<T> successByData(T data) {
        return new ResponseVO(ResponseEnum.SUCCESS.getStatus(), data);
    }
    public static <T> ResponseVO<T> success() {
        return new ResponseVO(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMsg());
    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum) {
        return new ResponseVO(responseEnum.getStatus(), responseEnum.getMsg());
    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVO(responseEnum.getStatus(), msg);
    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVO(responseEnum.getStatus(), bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
    }


}
