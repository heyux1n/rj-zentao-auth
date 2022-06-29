package com.youngyedu.rjzentaoauth.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: heyuxin
 * @Create: 2022-06-21
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAIL(999, "失败");


    /**
     * 代码
     */
    private int code;
    /**
     * 信息
     */
    private String msg;
}
