package com.cn.lx.advice;

import com.cn.lx.exception.AdException;
import com.cn.lx.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 同一的异常处理
 */
@RestControllerAdvice
public class GlobalException {

    //异常处理方法
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String > handlerAdException(HttpServletRequest req, AdException ex){
        CommonResponse<String> response = new CommonResponse<>(-1,"business error");
        response.setData(ex.getMessage());
        return response;
    }
}
