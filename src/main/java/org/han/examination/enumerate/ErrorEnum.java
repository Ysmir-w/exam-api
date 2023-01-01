package org.han.examination.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorEnum {
    ERROR(-1, "error"),
    NO_LOGIN(-2, "用户未登录"),
    NO_PERMISSIONS(-3, "没有权限"),
    SERVER_ERROR(-9, "服务器内部错误"),
    NOT_FOUND(404, "接口不存在");
    private final Integer code;
    private final String message;

}
