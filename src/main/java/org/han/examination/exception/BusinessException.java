package org.han.examination.exception;

import lombok.Getter;
import org.han.examination.enumerate.ErrorEnum;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorEnum error;

    public BusinessException(ErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public BusinessException(String message) {
        super(message);
        this.error = ErrorEnum.ERROR;
    }
}
