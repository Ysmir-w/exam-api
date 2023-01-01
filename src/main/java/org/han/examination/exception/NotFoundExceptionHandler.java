package org.han.examination.exception;

import lombok.extern.slf4j.Slf4j;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.result.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NotFoundExceptionHandler implements ErrorController {
    @GetMapping("/error")
    public Result<Void> error() {
        log.error("业务异常: {}", ErrorEnum.NOT_FOUND.getMessage());
        return Result.error(ErrorEnum.NOT_FOUND);
    }
}
