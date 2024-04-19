package com.cx.ordersproject.Server;

import com.cx.ordersproject.Common.GlobalException;
import com.cx.ordersproject.Pojo.VO.ErrorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ErrorVO exceptionHandler(GlobalException globalException){

        ErrorVO errorVO = new ErrorVO(globalException.getMessage());

        return errorVO;

    }

}
