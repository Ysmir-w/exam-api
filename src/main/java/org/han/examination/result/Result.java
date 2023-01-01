package org.han.examination.result;

import lombok.Data;
import org.han.examination.enumerate.ErrorEnum;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<Void> success() {
        return new Result<>(0, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }

    public static Result<Void> error() {
        return new Result<>(ErrorEnum.ERROR.getCode(), ErrorEnum.ERROR.getMessage(), null);
    }

    public static Result<Void> error(String message) {
        return new Result<>(ErrorEnum.ERROR.getCode(), message, null);
    }

    public static Result<Void> error(ErrorEnum error) {
        return new Result<>(error.getCode(), error.getMessage(), null);
    }
}
