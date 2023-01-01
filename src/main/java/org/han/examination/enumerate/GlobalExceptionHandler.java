package org.han.examination.enumerate;

import lombok.extern.slf4j.Slf4j;
import org.han.examination.exception.BusinessException;
import org.han.examination.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> businessExceptionHandler(BusinessException exception) {
        log.error("业务异常: {}", exception.getMessage(), exception);
        return exception.getError().getCode() != -1 ? Result.error(exception.getError()) : Result.error(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<Void> runtimeException(RuntimeException exception) {
        log.error("运行时异常: {}", exception.getMessage(), exception);
        return Result.error(ErrorEnum.SERVER_ERROR);
    }
}
