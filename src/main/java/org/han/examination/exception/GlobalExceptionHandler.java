package org.han.examination.exception;

import lombok.extern.slf4j.Slf4j;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.result.Result;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> businessExceptionHandler(BusinessException exception) {
        log.error("业务异常: {}", exception.getMessage(), exception);
        return exception.getError().getCode() != -1 ? Result.error(exception.getError()) : Result.error(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> bindExceptionHandler(BindException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" && "));
        log.error("参数异常: {}", message, exception);
        return Result.error(message);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" && "));
        log.error("参数异常: {}", message, exception);
        return Result.error(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<Void> runtimeExceptionHandler(RuntimeException exception) {
        log.error("运行时异常: {}", exception.getMessage(), exception);
        return Result.error(ErrorEnum.SERVER_ERROR);
    }
}
