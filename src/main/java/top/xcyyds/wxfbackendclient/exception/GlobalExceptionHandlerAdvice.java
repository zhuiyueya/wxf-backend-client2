package top.xcyyds.wxfbackendclient.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xcyyds.wxfbackendclient.common.Result;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-15
 * @Description:统一异常处理类
 * @Version:v1
 */

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    @ExceptionHandler
    public Result handleException(Exception e, HttpServletResponse response, HttpServletRequest request) {
        return new Result(402,"error",null);
    }
}