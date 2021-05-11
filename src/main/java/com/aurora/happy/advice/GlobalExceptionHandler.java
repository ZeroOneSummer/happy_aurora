package com.aurora.happy.advice;

import com.aurora.happy.common.Result;
import com.aurora.happy.enums.ExceptionCodeEnum;
import com.aurora.happy.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException bizException) {
        log.error("业务异常:{}", bizException.getMessage(), bizException);
        return Result.error(bizException.getError());
    }

    /**
     * 运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRunTimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return Result.error(ExceptionCodeEnum.ERROR);
    }

}