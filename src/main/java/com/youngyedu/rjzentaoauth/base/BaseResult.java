package com.youngyedu.rjzentaoauth.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: heyuxin
 * @Create: 2022-06-21
 * @Description: rest请求统一返回值
 */
@AllArgsConstructor
@Data
public class BaseResult<T> {
    private int code;

    private String message;

    private T data;



    public static <T> BaseResult<T> success(T data) {
        return new BaseResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }


    public static <T> BaseResult<T> fail(String msg) {
        return new BaseResult(ResultEnum.FAIL.getCode(), msg, null);
    }

    public static <T> BaseResult<T> fail(int code, String msg) {
        return new BaseResult(code, msg, null);
    }
}
