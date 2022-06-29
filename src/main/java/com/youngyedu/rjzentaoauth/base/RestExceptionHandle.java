package com.youngyedu.rjzentaoauth.base;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: heyuxin
 * @Create: 2022-06-21
 * @Description:
 */
@RestControllerAdvice
public class RestExceptionHandle {

    @ExceptionHandler(Exception.class)
    public BaseResult<String> exception(Exception e) {
        e.printStackTrace();
        return BaseResult.fail(e.getMessage());
    }
}
